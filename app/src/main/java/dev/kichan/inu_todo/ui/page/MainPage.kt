package dev.kichan.inu_todo.ui.page

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.inu_todo.MainActivity

@Preview(showBackground = true)
@Composable
fun MainPage(navController: NavController = rememberNavController()) {
    Column(
        Modifier.fillMaxSize()
    ) {
        Button(onClick = {
            navController.navigate(Page.SIGN_UP.name)
        }) {
            Text(text = "회원가입")
        }
        Button(onClick = { navController.navigate(Page.SIGN_IN.name) }) {
            Text(text = "로그인")
        }
    }
}