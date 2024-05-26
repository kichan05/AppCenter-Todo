package dev.kichan.inu_todo.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.inu_todo.ui.theme.Blue_400
import dev.kichan.inu_todo.ui.theme.INUTodoTheme
import dev.kichan.inu_todo.ui.theme.suit

@Composable
fun Header(title : String, onNavigationClick : () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            Modifier.align(Alignment.Center),
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = suit,
                fontWeight = FontWeight.Medium
            )
        )

        IconButton(onClick = onNavigationClick) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = null,
                tint = Blue_400
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HerderPreview() {
    INUTodoTheme {
        Header(title = "회원가입") {  }
    }
}