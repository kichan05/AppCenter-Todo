package dev.kichan.inu_todo.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun MainPage() {
    Column {
        Button(onClick = { /*TODO*/ }) {
            Text(text = "회원가입")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "로그인")
        }
    }
}