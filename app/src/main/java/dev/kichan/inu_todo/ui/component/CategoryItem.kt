package dev.kichan.inu_todo.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import dev.kichan.inu_todo.ui.theme.INUTodoTheme
import dev.kichan.inu_todo.ui.theme.suit

@Composable
fun CategoryItem(category: Category) {
    val shape = RoundedCornerShape(50.dp)

    Text(
        text = category.content,
        modifier = Modifier
            .background(category.colorValue.copy(alpha = 0.3f), shape)
            .border(1.dp, category.colorValue, shape)
            .padding(vertical = 7.dp, horizontal = 15.dp),
        style = TextStyle(
            color = Color(0xff735B37),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = suit,
        )
    )
}

@Preview(showBackground = true)
@Composable
fun CategoryItemPreview() {
    val category = Category(categoryId = 7830, content = "학교", color = CategoryColor.GREEN.hex)

    INUTodoTheme {
        CategoryItem(category)
    }
}