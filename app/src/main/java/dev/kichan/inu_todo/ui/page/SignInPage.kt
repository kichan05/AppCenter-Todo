package dev.kichan.inu_todo.ui.page

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.inu_todo.MainActivity
import dev.kichan.inu_todo.model.RetrofitBuilder
import dev.kichan.inu_todo.model.data.member.SignInReq
import dev.kichan.inu_todo.model.service.MemberService
import dev.kichan.inu_todo.ui.component.Header
import dev.kichan.inu_todo.ui.component.InputLabel
import dev.kichan.inu_todo.ui.component.InuButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Preview(showBackground = true)
@Composable
fun SignInPage(navController: NavController = rememberNavController()) {
    val id = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val signIn: (String, String) -> Unit = { id, pass ->
        CoroutineScope(Dispatchers.IO).launch {
            val service = RetrofitBuilder.getService(MemberService::class.java)
            val result = service.signIn(
                body = SignInReq(
                    userId = id,
                    userPw = pass
                )
            )

            if (result.isSuccessful) {
                MainActivity.user = result.body()!!

                withContext(Dispatchers.Main) {
                    navController.navigate(Page.Home.name)
                    navController.clearBackStack(Page.SIGN_IN.name)
                    navController.clearBackStack(Page.MAIN.name)
                }

                Log.d("SignIn", "성공")
            } else {
                Log.d("SignIn", "실패")
            }

            Log.d("SignIn", result.body().toString())
        }
    }

    Column(Modifier.fillMaxSize()) {
        Header(title = "로그인") { }

        Column {
            InputLabel(
                value = id.value,
                onChange = { id.value = it },
                label = "아이디",
                placeholder = "아이디를 입력해주세요",
                modifier = Modifier.fillMaxWidth(),
                icon = Icons.Default.Person
            )

            InputLabel(
                value = password.value,
                onChange = { password.value = it },
                label = "비밀번호",
                placeholder = "비밀번호를 입력해주세요",
                modifier = Modifier.fillMaxWidth(),
                icon = Icons.Default.Person
            )
        }

        InuButton(onClick = { /*TODO*/ }, text = "로그인")

//        Button(onClick = { signIn(id.value, password.value) }) {
//            Text(text = "로그인")
//        }
    }
}