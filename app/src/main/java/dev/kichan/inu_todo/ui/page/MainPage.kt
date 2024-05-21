package dev.kichan.inu_todo.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Preview(showBackground = true)
@Composable
fun MainPage(navController: NavController = rememberNavController()) {
    Column {
        Button(onClick = { navController.navigate(Page.SIGN_UP.name) }) {
            Text(text = "회원가입")
        }
        Button(onClick = { navController.navigate(Page.SIGN_IN.name) }) {
            Text(text = "로그인")
        }
    }
}