package at.zimmercarral.mealcalc.data.http

import android.util.Log
import at.zimmercarral.mealcalc.data.db.model.FoodItem
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.math.roundToInt

val GSON = Gson()
fun asyncGetHttpRequest(
    endpoint: String,
    onSuccess: (ApiResponse<List<FoodItem>>) -> Unit,
    onError: (Exception) -> Unit
) {
    CoroutineScope(Dispatchers.IO).launch {
        val url = URL(endpoint)
        val openedConnection = url.openConnection() as HttpURLConnection
        openedConnection.requestMethod = "GET"
        var reader: BufferedReader? = null
        try {
            val responseCode = openedConnection.responseCode
            reader = BufferedReader(InputStreamReader(openedConnection.inputStream))
            val mapper = jacksonObjectMapper()
            mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true)
            val httpItems: List<HttpFoodItem> = mapper.readValue(reader,
                mapper.typeFactory.constructCollectionType(List::class.java,
                    HttpFoodItem::class.java))

            val apiResponse = ApiResponse(responseCode, convertItems(httpItems))
            // Call the success callback on the main thread
            launch(Dispatchers.Main) {
                onSuccess(apiResponse)
            }
        } catch (e: Exception) {
            Log.d("Error", e.message.toString())
            print(e)
            // Handle error cases and call the error callback on the main thread
            launch(Dispatchers.Main) {
                onError(Exception("HTTP Request failed with response code "))
            }
        } finally {
            reader?.close()
        }
    }
}

fun convertItems(httpItems: List<HttpFoodItem>): List<FoodItem> {
    return httpItems.map({ item ->
        FoodItem(
            name = item.name ?: "",
            uuid = item.uuid ?: "",
            fatPer100g = toFloat(item.fat),
            carbsPer100g = toFloat(item.carbs),
            proteinPer100g = toFloat(item.proteine),
            amount = toInt(item.amount),
            lastModifiedAt = convertDate(item.lastchanged),
            isDeleted = convertBool(item.deleted ?: "0"))
    })
}

fun toInt(stringValue: String?): Int {
    var intValue = 0
    if (stringValue != null && stringValue != "" && stringValue != "null") {
        intValue = stringValue.toFloat().roundToInt()
    }
    return intValue
}

private fun toFloat(stringValue: String?): Float {
    var floatValue = 0.0f
    if (stringValue != null && stringValue != "" && stringValue != "null") {
        floatValue = stringValue.toFloat()
    }
    return floatValue
}

// For input string: "2022-06-12T08:19:44.000Z"
fun convertDate(dateString: String?): LocalDateTime? {
    if (dateString != null && dateString != "null" && dateString != ""
        && dateString != "0000-00-00 00:00:00"
    ) {
        val instant = Instant.parse(dateString)
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    }
    return null
}

fun convertBool(deleted: String): Boolean {
    if (deleted != null && deleted != "null" && deleted != "") {
        return deleted.toInt() == 1
    }
    return false
}

data class ApiResponse<T>(
    val responseCode: Int,
    val response: T
)


