package at.zimmercarral.mealcalc.data.http

import at.zimmercarral.mealcalc.data.db.model.FoodItem

class HttpFoodItemRepository() {

    companion object {
        private const val BASE_URL = "http://192.168.0.45:3000/"
        private fun getAbsoluteUrl(relativeUrl: String) = "$BASE_URL$relativeUrl"
    }

    fun getEntries(onSuccess: (List<FoodItem>) -> Unit) {
        val url = getAbsoluteUrl("entries")

        asyncGetHttpRequest(endpoint = url,
            onSuccess = { apiResponse: ApiResponse<List<FoodItem>> ->
                if (apiResponse.responseCode == 200) {
                    onSuccess(apiResponse.response)
                }
            },
            onError = ({ e ->
                print(e)
            }))
    }

    //    fun saveEntry(foodItem: FoodItem) {
//        val url = getAbsoluteUrl("entry")
//        try {
//            val jsonObject = getJsonObject(foodItem)
//            val jsObjRequest = JsonObjectRequest(
//                Request.Method.POST,
//                url,
//                jsonObject,
//                { response -> println(response) },
//                { error -> println(error) }
//            )
//            requestQueue.add(jsObjRequest)
//        } catch (e: JSONException) {
//            println(e.message)
//        }
//    }
//
//    fun updateEntry(foodItem: FoodItem) {
//        try {
//            val jsonObject = getJsonObject(foodItem)
//            val url = getAbsoluteUrl("entry")
//
//            val jsObjRequest = JsonObjectRequest(
//                Request.Method.PUT,
//                url,
//                jsonObject,
//                { response -> println(response) },
//                { error -> println(error) }
//            )
//            requestQueue.add(jsObjRequest)
//        } catch (e: JSONException) {
//            println(e.message)
//        }
//    }
//
//    @Throws(JSONException::class)
//    private fun getJsonObject(foodItem: FoodItem): JSONObject {
//        return JSONObject().apply {
//            put("uuid", foodItem.uuid)
//            put("name", foodItem.name)
//            put("fat", foodItem.fatPer100g)
//            put("carbs", foodItem.carbsPer100g)
//            put("protein", foodItem.proteinPer100g)
//            put("amount", foodItem.amount)
//            put("lastchanged", foodItem.lastModifiedAt)
//        }
//    }
}