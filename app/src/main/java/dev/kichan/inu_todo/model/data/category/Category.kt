package dev.kichan.inu_todo.model.data.category

import androidx.compose.ui.graphics.Color

data class Category(
    val categoryId: Int,
    val content: String,
    val color: String,
) {
    val colorValue : Color
        get() {
            val color = Color(android.graphics.Color.parseColor("#${this.color}"))
            return color
        }
}