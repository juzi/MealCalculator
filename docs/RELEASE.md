# Releasing MealCalculator on Google Play

Everything here is done with the Google account **jzimmer1000@gmail.com**, which owns
the Firebase project `mealcalculator-584e6` and the Play Console developer account.

Steps marked *(once)* are only needed for the first release.

## 1. Firebase *(once)*

1. **Register the Android app.** Firebase console → Project settings → *Add app* →
   Android, package name `net.zimmercarral.mealcalculator`. Add the debug SHA-1
   from `./gradlew signingReport`. Download `google-services.json` to `app/`.
   (The old `at.zimmercarral.mealcalc` app entry can be deleted afterwards.)
2. **Google sign-in support email.** Authentication → Sign-in method → Google:
   support email `jzimmer1000@gmail.com`.
3. **OAuth consent screen.** Google Cloud console (same project) → *Google Auth
   Platform* → Branding: app name *MealCalculator*, support and developer contact
   `jzimmer1000@gmail.com`, privacy policy URL (below). Under *Audience*, set the
   publishing status to **In production**. While it says *Testing*, only listed
   test users can sign in. Only the basic scopes (email, profile) are used, so
   no Google verification is needed.
4. **Hosting: privacy policy and account deletion pages.**

       npm install -g firebase-tools   # once
       firebase login
       firebase deploy --only hosting,firestore:rules

   The pages are then live at:
   - https://mealcalculator-584e6.web.app/privacy
   - https://mealcalculator-584e6.web.app/delete-account

## 2. Upload key and signing *(once)*

Create the upload key and keep it plus its passwords safe (password manager +
backup). Losing it means a key reset through Play support.

    keytool -genkeypair -v -keystore ~/keys/mealcalculator-upload.jks \
        -alias upload -keyalg RSA -keysize 4096 -validity 10000

Create `keystore.properties` in the project root (gitignored):

    storeFile=/home/<you>/keys/mealcalculator-upload.jks
    storePassword=...
    keyAlias=upload
    keyPassword=...

Use **Play App Signing** (the default for new apps): Google holds the app
signing key, you sign uploads with the upload key.

## 3. Play Console app setup *(once)*

Create the app: name *MealCalculator*, default language English (United States),
type *App*, *Free*.

### App content

| Section | Answer |
|---|---|
| Privacy policy | `https://mealcalculator-584e6.web.app/privacy` |
| App access | All functionality is available without special access. Sign-in is optional and uses the reviewer's own Google account. |
| Ads | No ads |
| Content rating | IARC questionnaire, category *Utility/Productivity*, answer *No* everywhere. Expected rating: Everyone / PEGI 3 |
| Target audience | 18 and over. This does not restrict who can download the app and avoids the Families policy requirements. |
| News app | No |
| Health apps | Tick only **Nutrition and weight management**. The app is not a medical device and makes no medical claims. |
| Government app / Financial features | No / none |
| Data safety | see below |

### Data safety

Check the answers against Firebase's
[Play data disclosure page](https://firebase.google.com/docs/android/play-data-disclosure)
before submitting; it lists what each Firebase SDK collects.

- **Does your app collect or share user data?** Yes.
- **Is all data encrypted in transit?** Yes.
- **Can users request data deletion?** Yes, in the app and at
  `https://mealcalculator-584e6.web.app/delete-account`.
- **Shared with third parties:** none. Firebase acts as a service provider, which does not count as sharing.
- **Collected** (all *optional*, only when the user signs in to sync):

  | Data type | Purposes |
  |---|---|
  | Personal info → Email address | App functionality, Account management |
  | Personal info → Name | App functionality, Account management |
  | Personal info → User IDs | App functionality, Account management |
  | App activity → Other user-generated content (food items) | App functionality |

  Data is processed ephemerally: No. Collection is required: No, users can choose (sign-in is optional).

### Store listing

Category **Food & Drink**. Contact email `jzimmer1000@gmail.com`. Website
`https://mealcalculator-584e6.web.app`.

Graphics are in [`store/`](../store):
- App icon: `store/icon-512.png` (512×512)
- Feature graphic: `store/feature-graphic.png` (1024×500)
- Phone screenshots: `store/screenshots/*.png` (1080×2160)

**English (en-US)**

- App name: `MealCalculator`
- Short description (max 80):
  `Add up carbs, fat and protein of your meal – quick, simple and offline.`
- Full description:

  ```
  MealCalculator adds up the nutritional values of your meal.

  Save the foods you eat with their carbs, fat and protein per 100 g. When you eat,
  add them to your meal with the amount in grams, and MealCalculator shows the
  total carbs, fat and protein at a glance.

  • Your own food database: save each food once, find it again with search
  • Totals for each item and for the whole meal
  • Edit amounts with a long press, swipe to remove an item
  • Works completely offline, no account needed
  • Optional: sign in with Google to sync your foods across devices
  • Light and dark mode, English and German
  • No ads, no tracking

  Your data stays on your device unless you turn on sync. You can delete your
  account and all cloud data in the app at any time.
  ```

**German (de-DE)**

- App name: `MealCalculator`
- Short description (max 80):
  `Kohlenhydrate, Fett und Eiweiß deiner Mahlzeit schnell zusammenrechnen.`
- Full description:

  ```
  MealCalculator rechnet die Nährwerte deiner Mahlzeit zusammen.

  Speichere deine Lebensmittel mit Kohlenhydraten, Fett und Eiweiß pro 100 g.
  Beim Essen fügst du sie mit der Menge in Gramm zur Mahlzeit hinzu, und
  MealCalculator zeigt dir sofort die Summe an Kohlenhydraten, Fett und Eiweiß.

  • Eigene Lebensmittel-Datenbank: einmal speichern, per Suche wiederfinden
  • Summen für jedes Lebensmittel und die ganze Mahlzeit
  • Mengen per langem Tippen ändern, Einträge zum Entfernen wischen
  • Funktioniert komplett offline, kein Konto nötig
  • Optional: mit Google anmelden und Lebensmittel zwischen Geräten synchronisieren
  • Heller und dunkler Modus, Deutsch und Englisch
  • Keine Werbung, kein Tracking

  Deine Daten bleiben auf deinem Gerät, solange du die Synchronisation nicht
  einschaltest. Konto und Cloud-Daten kannst du jederzeit in der App löschen.
  ```

## 4. Build and upload (every release)

1. Increase `versionCode` (and `versionName`) in `app/build.gradle.kts`.
2. Build the signed bundle:

       ./gradlew clean testDebugUnitTest bundleRelease

   Output: `app/build/outputs/bundle/release/app-release.aab`.
   R8 mapping file for crash deobfuscation:
   `app/build/outputs/mapping/release/mapping.txt` (Play uploads it from the bundle).
3. Upload to a testing track in the Play Console.

**First release, new personal developer account:** Play requires a *closed test*
with at least 12 testers who stay opted in for 14 days before you can apply for
production access. Start this early.

## 5. After the first upload *(once)*

1. **Add both SHA fingerprints to Firebase.** Play Console → Test and release →
   *App integrity* → App signing: copy the SHA-1 and SHA-256 of the **app signing
   key** and of the **upload key** into the Firebase Android app. Without the
   app signing key's SHA-1, Google sign-in fails in the version installed from
   Play.
2. **App Check.**
   - Play Console → App integrity → Play Integrity API: link the Google Cloud
     project `mealcalculator-584e6`.
   - Firebase console → App Check → Apps: register the Android app with the
     *Play Integrity* provider (needs the app signing key's SHA-256).
   - Debug builds log a debug token on first launch (`adb logcat | grep
     DebugAppCheckProvider`). Add it under App Check → *Manage debug tokens*.
   - Watch the App Check metrics for Cloud Firestore for a few days. When nearly
     all requests are verified, click **Enforce**. Unverified clients (old
     versions, modified apps) are then rejected.
3. **Test on a real device** with a throwaway Google account: sign in, sync,
   *Delete account…*. Then check in the Firebase console that the user and their
   `users/{uid}/foodItems` documents are gone.
