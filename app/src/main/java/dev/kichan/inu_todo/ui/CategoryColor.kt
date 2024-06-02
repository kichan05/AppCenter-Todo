package dev.kichan.inu_todo.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

enum class CategoryColor(val color : Color) {
    YELLOW(Color(0xffFFE560)),
    GREEN(Color(0xff47D2CA)),
    PINK(Color(0xffF9B0CA)),
    BLUE(Color(0xffB6B0F9));

    val hex: String
        get() {
            val argb = this.color.toArgb()
            return String.format("%06X", argb and 0xFFFFFF)
        }
}