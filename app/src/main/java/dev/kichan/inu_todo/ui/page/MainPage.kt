package dev.kichan.inu_todo.ui.page

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.inu_todo.MainActivity
import dev.kichan.inu_todo.model.RetrofitBuilder
import dev.kichan.inu_todo.model.data.member.SignInReq
import dev.kichan.inu_todo.model.data.member.User
import dev.kichan.inu_todo.model.service.MemberService
import dev.kichan.inu_todo.ui.component.InuButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun MainPage(navController: NavController = rememberNavController()) {
    val signIn : (String, String) -> Unit = {id, pass ->
        CoroutineScope(Dispatchers.IO).launch {
            val service = RetrofitBuilder.getService(MemberService::class.java)
            val result = service.signIn(
                body = SignInReq(
                    userId = id,
                    userPw = pass
                )
            )

            if(result.isSuccessful) {
                MainActivity.user = result.body()!!
                Log.d("SignIn", "성공")
            }
            else {
                Log.d("SignIn", "실패")
            }

            Log.d("SignIn", result.body().toString())
        }
    }

    signIn("android", "qwer1234!")

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