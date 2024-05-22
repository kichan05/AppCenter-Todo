package dev.kichan.inu_todo.ui.component

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.inu_todo.ui.theme.Blue_400
import dev.kichan.inu_todo.ui.theme.INUTodoTheme

@Composable
fun MainButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    Column(
        modifier
            .background(color = Blue_400, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 64.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "버튼",
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
        )
    }
}

@Preview
@Composable
fun MainButtonPreview() {
    INUTodoTheme {
        MainButton(
            modifier = Modifier.fillMaxWidth(),
            text = "버튼",
            onClick = {}
        )
    }
}