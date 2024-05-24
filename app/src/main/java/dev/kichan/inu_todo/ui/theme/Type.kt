package dev.kichan.inu_todo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.kichan.inu_todo.R

val suit = FontFamily(
    Font(R.font.suit_regular, FontWeight.Normal),
    Font(R.font.suit_bold, FontWeight.Bold),
    Font(R.font.suit_extrabold, FontWeight.ExtraBold),
    Font(R.font.suit_extralight, FontWeight.ExtraLight),
    Font(R.font.suit_heavy, FontWeight.Black),
    Font(R.font.suit_light, FontWeight.Light),
    Font(R.font.suit_medium, FontWeight.Medium),
    Font(R.font.suit_semibold, FontWeight.SemiBold),
    Font(R.font.suit_thin, FontWeight.Thin)
)

val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = suit,
        fontWeight = FontWeight.Medium,
    ),
    bodyLarge = TextStyle(
        fontFamily = suit,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)