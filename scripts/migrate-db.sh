#!/usr/bin/env bash
#
# Copies the local food-item database from the old app (at.zimmercarral.mealcalc)
# to the renamed app (net.zimmercarral.mealcalculator) over adb.
#
# Both apps use the same Room database ("food-items.db", version 2, identical
# "fooditems" table), so the files are copied as-is without conversion.
#
# Requirements:
#   - USB debugging enabled, exactly one device connected (or ANDROID_SERIAL set)
#   - both apps are debug builds (installed from Android Studio / via USB),
#     because "run-as" only works for debuggable apps
#   - for import: the new app has been opened once
#
# Usage:
#   scripts/migrate-db.sh           # export from old app, then import into new app
#   scripts/migrate-db.sh export    # only copy the old app's database to EXPORT_DIR
#   scripts/migrate-db.sh import    # only load EXPORT_DIR into the new app
#
# Environment:
#   EXPORT_DIR  where the database copy is kept (default: ~/mealcalc-export)
#   ADB         adb binary (default: adb on PATH, else ~/Android/Sdk/platform-tools/adb)

set -euo pipefail

OLD_PKG=at.zimmercarral.mealcalc
NEW_PKG=net.zimmercarral.mealcalculator
DB=food-items.db
DB_FILES=("$DB" "$DB-wal" "$DB-shm")
EXPORT_DIR=${EXPORT_DIR:-$HOME/mealcalc-export}
DEVICE_TMP=/data/local/tmp/mealcalc-migrate

if [[ -z ${ADB:-} ]]; then
    if command -v adb >/dev/null; then
        ADB=adb
    else
        ADB=$HOME/Android/Sdk/platform-tools/adb
    fi
fi

die() { echo "error: $*" >&2; exit 1; }

check_device() {
    "$ADB" get-state >/dev/null 2>&1 \
        || die "no device connected (or several: set ANDROID_SERIAL)"
}

check_debuggable() {
    local pkg=$1
    "$ADB" shell run-as "$pkg" id >/dev/null 2>&1 \
        || die "cannot run-as $pkg: app not installed or not a debug build"
}

export_db() {
    check_debuggable "$OLD_PKG"
    "$ADB" shell run-as "$OLD_PKG" ls "databases/$DB" >/dev/null 2>&1 \
        || die "$OLD_PKG has no databases/$DB"

    mkdir -p "$EXPORT_DIR"
    rm -f "${DB_FILES[@]/#/$EXPORT_DIR/}"

    # Stop the app so nothing writes to the database while it is copied.
    "$ADB" shell am force-stop "$OLD_PKG"
    for f in "${DB_FILES[@]}"; do
        if "$ADB" shell run-as "$OLD_PKG" ls "databases/$f" >/dev/null 2>&1; then
            "$ADB" exec-out run-as "$OLD_PKG" cat "databases/$f" > "$EXPORT_DIR/$f"
            echo "exported $f ($(wc -c < "$EXPORT_DIR/$f") bytes)"
        fi
    done

    if command -v sqlite3 >/dev/null; then
        echo "items in export: $(sqlite3 "$EXPORT_DIR/$DB" 'select count(*) from fooditems')"
    fi
    echo "export saved in $EXPORT_DIR"
}

import_db() {
    [[ -f $EXPORT_DIR/$DB ]] || die "no $DB in $EXPORT_DIR, run export first"
    check_debuggable "$NEW_PKG"

    local present=()
    for f in "${DB_FILES[@]}"; do
        [[ -f $EXPORT_DIR/$f ]] && present+=("$EXPORT_DIR/$f")
    done

    "$ADB" shell am force-stop "$NEW_PKG"
    "$ADB" shell "rm -rf $DEVICE_TMP && mkdir -p $DEVICE_TMP"
    "$ADB" push "${present[@]}" "$DEVICE_TMP/" >/dev/null
    "$ADB" shell chmod 644 "$DEVICE_TMP/*"
    # Remove the new app's database including stale -wal/-shm files, which
    # would otherwise not match the copied database.
    "$ADB" shell run-as "$NEW_PKG" sh -c \
        "'mkdir -p databases && rm -f databases/$DB databases/$DB-wal databases/$DB-shm && cp $DEVICE_TMP/* databases/'"
    "$ADB" shell rm -rf "$DEVICE_TMP"

    echo "imported into $NEW_PKG:"
    "$ADB" shell run-as "$NEW_PKG" ls -l databases
    echo "start the app to see the items"
}

check_device
case ${1:-all} in
    export) export_db ;;
    import) import_db ;;
    all)    export_db; import_db ;;
    *)      die "usage: $0 [export|import]" ;;
esac
