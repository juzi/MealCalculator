# Keep crash-stack line numbers and source file mapping (rename file for privacy).
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Firestore POJO mapping uses reflection on the no-arg constructor and field names.
-keep class at.zimmercarral.mealcalc.data.cloud.FirestoreFoodItem { *; }

# Gson serializes meal items by reflecting on the data class fields.
-keep class at.zimmercarral.mealcalc.data.db.model.FoodItem { *; }
