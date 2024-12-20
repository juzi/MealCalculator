package at.zimmercarral.mealcalc.data.http

data class HttpFoodItem(
    var id: String,
    var uuid: String?,
    var name: String?,
    var fat: String?,
    var carbs: String?,
    var proteine: String?,
    var amount: String?,
    var lastchanged: String?,
    var deleted: String?
)
