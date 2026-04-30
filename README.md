# MealCalculator

An Android app for tracking food items with macros (fat, carbs, protein) and an
insulin-pump reminder, intended for diabetics. Food items are stored locally in
Room/SQLite and synced to Cloud Firestore so they follow the user across
devices and reinstalls.

## Architecture

- **Local database** — Room/SQLite (`food-items.db`). One table, `fooditems`,
  keyed by client-generated `uuid`. Source of truth on the device.
- **Cloud sync** — Cloud Firestore. Each user's items live under
  `users/{uid}/foodItems/{uuid}`. A snapshot listener mirrors remote changes
  into Room; writes go to Room first, then Firestore.
- **Auth** — Firebase Authentication with Google Sign-In via the modern
  Credential Manager API. The app shows a sign-in screen on first launch and
  whenever the user is signed out.
- **Conflict resolution** — last-write-wins on `lastModifiedAt`. Deletes are
  soft (an item with `isDeleted = true` in Firestore causes a hard delete in
  Room).

The local-network MySQL/REST sync that earlier versions used has been removed.

## Prerequisites

- Android Studio (current stable), Android SDK 35
- JDK 17
- A Google account for the Firebase project

## One-time Firebase setup

The app will not build until `app/google-services.json` is present, and will
not sign in until the Firebase project is configured. Do this once:

1. **Create a Firebase project**
   Go to <https://console.firebase.google.com>, create a project (any name).

2. **Add an Android app**
   In project settings → *Your apps* → *Add app* → Android.
   - Package name: `at.zimmercarral.mealcalc`
   - Nickname: anything (e.g. `MealCalculator dev`)
   - Debug signing certificate SHA-1: run

         ./gradlew signingReport

     and copy the SHA-1 from the `:app debug` variant. Add the same way for
     a release keystore when you ship.

3. **Download `google-services.json`**
   Save it to `app/google-services.json`. This file is gitignored — every
   developer adds their own.

4. **Enable Firestore**
   Build → *Firestore Database* → *Create database* → *Native mode* →
   pick a region close to your users. Start in *production mode* (the rules
   file in this repo locks data down per user).

5. **Deploy security rules**
   Either install the Firebase CLI and run

       firebase deploy --only firestore:rules

   or paste the contents of `firestore.rules` into the Firebase console
   under *Firestore* → *Rules*.

6. **Enable Google sign-in**
   Build → *Authentication* → *Sign-in method* → *Google* → enable. Set the
   support email. This step also creates the OAuth 2.0 *Web client ID* that
   Credential Manager needs; it lands in `google-services.json` automatically
   the next time you download it. After enabling, **redownload
   `google-services.json`** so it includes the web client.

7. **Verify**
   The Gradle `google-services` plugin generates a `default_web_client_id`
   string resource from `google-services.json`. The app reads it via
   `R.string.default_web_client_id`. If sign-in fails with a "missing web
   client" error, the file does not contain a web OAuth client — re-do
   step 6, redownload, and rebuild.

## Build and run

    ./gradlew assembleDebug
    ./gradlew installDebug

Or open the project in Android Studio and Run.

On first launch the user sees a *Sign in with Google* button. After signing
in, the snapshot listener pulls any existing food items from Firestore and
pushes any local-only items up. Subsequent edits (add/update via the edit
dialog) write to Room and Firestore in lock-step.

## Free-tier limits

A single Firebase project on the free *Spark* plan covers Firestore reads
and writes well within the budget for personal-scale use. If you publish the
app, expect each active user to generate a few dozen reads and writes per
day; Firestore's free quota is 50k reads + 20k writes per day across the
whole project.

## Project layout

    app/src/main/java/at/zimmercarral/mealcalc/
      MainActivity.kt              # gates UI on FirebaseAuth state
      MainLayout.kt                # main composable (search, edit, meal)
      data/
        auth/AuthManager.kt        # Credential Manager + FirebaseAuth bridge
        auth/SignInScreen.kt       # Compose sign-in UI
        cloud/FirestoreFoodItem.kt # DTO + mapping to/from FoodItem
        cloud/FirestoreFoodItemRepository.kt  # snapshot listener + upsert
        cloud/FirestoreSyncService.kt         # listener-driven Room mirror
        db/...                     # Room entities, DAO, type converters
        repo/FoodItemRepository.kt # write-through wrapper (Room + cloud)
      edit/, meal/, search/, ui/   # Compose UI

## Removing a user

Firestore data lives at `users/{uid}/foodItems/*`. To wipe a user, delete
their document subtree (Firebase console or CLI). Anonymous-account-style
data loss is not a concern here because every user is identified by a real
Google account.

## Troubleshooting

- **`Missing google-services.json`** during build — complete *One-time
  Firebase setup* step 3.
- **Sign-in fails immediately** with no account picker — usually means the
  debug SHA-1 was not added to the Firebase Android app, or `google-services.json`
  is stale. Re-add the SHA-1, redownload, rebuild.
- **`PERMISSION_DENIED` in Firestore logs** — the rules in `firestore.rules`
  were not deployed. Deploy them.
- **Items not syncing after sign-in** — check `adb logcat | grep -i firestore`.
  If you see `UNAVAILABLE` errors the device is offline; Firestore will sync
  when connectivity returns (its own offline cache handles this).
