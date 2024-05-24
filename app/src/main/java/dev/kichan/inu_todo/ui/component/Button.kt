package dev.kichan.inu_todo.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.inu_todo.ui.theme.Blue_400
import dev.kichan.inu_todo.ui.theme.INUTodoTheme
import dev.kichan.inu_todo.ui.theme.suit

@Composable
fun InuButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = Blue_400,
    shape: Shape = RoundedCornerShape(12.dp),
    border: BorderStroke? = null,
    isDisable: Boolean = false,
) {
    Column(
        modifier
            .clickable { onClick() }
            .background(color = backgroundColor, shape = shape)
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .apply {
               if(border != null)
                   this.border(border)
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = Color.White,
                fontFamily = suit,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
        )
    }
}

@Preview
@Composable
fun InuButtonPreview() {
    INUTodoTheme {
        InuButton(
            modifier = Modifier.fillMaxWidth(),
            text = "버튼",
            onClick = {},
            border = BorderStroke(5.dp, Color.Red)
        )
    }
}