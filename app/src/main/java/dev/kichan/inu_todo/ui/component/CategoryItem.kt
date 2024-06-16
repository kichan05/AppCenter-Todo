package dev.kichan.inu_todo.ui.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.inu_todo.model.data.category.Category
import dev.kichan.inu_todo.ui.CategoryColor
import dev.kichan.inu_todo.ui.theme.Gray_300
import dev.kichan.inu_todo.ui.theme.Gray_400
import dev.kichan.inu_todo.ui.theme.INUTodoTheme
import dev.kichan.inu_todo.ui.theme.suit

@Composable
fun CategoryItem(category: Category, modifier: Modifier = Modifier, isEnable: Boolean = true) {
    Log.d("[Category]", category.toString())
    val backgroundColorMap = hashMapOf(
        "FFE560" to Color(0xffFFFDEE),
        "F9B0CA" to Color(0xffFFF5F9),
        "47D2CA" to Color(0xffF0FFFE),
        "B6B0F9" to Color(0xffF2F1FF),
    )

    val shape = RoundedCornerShape(50.dp)
    val textColor = if (isEnable) Color(0xff735B37) else Color(0xffd0d0d0)
    val backgroundColor = if (!isEnable || category.content == "기본") Color.White else backgroundColorMap[category.color]!!
    val borderColor = if (!isEnable) Color(0xffd0d0d0)
        else if (category.content == "기본") Gray_400
        else category.colorValue

    Text(
        text = category.content,
        modifier = modifier
            .background(backgroundColor, shape)
            .border(1.dp, borderColor, shape)
            .padding(vertical = 7.dp, horizontal = 15.dp),
        style = TextStyle(
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = suit,
        )
    )
}

@Preview(showBackground = true)
@Composable
fun CategoryItemPreview() {
    val category = Category(categoryId = 7830, content = "학교", color = CategoryColor.BLUE.hex)

    INUTodoTheme {
        Row {
            CategoryItem(category, isEnable = true)
            CategoryItem(category, isEnable = false)
        }
    }
}