package dev.kichan.inu_todo.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.inu_todo.MainActivity
import dev.kichan.inu_todo.model.data.member.User
import dev.kichan.inu_todo.ui.component.InuButton

@Preview(showBackground = true)
@Composable
fun MainPage(navController: NavController = rememberNavController()) {
    Column(
        Modifier.fillMaxSize()
    ) {
        InuButton(
            onClick = { navController.navigate(Page.SIGN_IN.name) },
            modifier = Modifier.fillMaxWidth(),
            text = "로그인",
        )

        InuButton(
            onClick = { navController.navigate(Page.SIGN_UP.name) },
            modifier = Modifier.fillMaxWidth(),
            text = "회원가입",
        )

        InuButton(
            onClick = { navController.navigate(Page.Home.name) },
            modifier = Modifier.fillMaxWidth(),
            text = "홈",
        )
    }
}