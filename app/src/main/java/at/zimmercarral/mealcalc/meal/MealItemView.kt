package at.zimmercarral.mealcalc.meal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import at.zimmercarral.mealcalc.data.db.model.FoodItem

@Composable
fun MealItemView(item: FoodItem, setCurrentItem: (FoodItem) -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(35.dp)
        .border(width = 0.5.dp, color = Color(160, 160, 160, 255),
            shape = RoundedCornerShape(7.dp)
        ),
        shape = RoundedCornerShape(7.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(225, 255, 225, 255))
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = { setCurrentItem(item) }
                    )
                },
        ) {
            Text(modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
                fontFamily = androidx.compose.ui.text.font.FontFamily.SansSerif,
                text = item.name + " (" + item.amount.toString() + "gr)")
        }
    }
}