package dev.kichan.inu_todo.ui.page

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.inu_todo.MainActivity
import dev.kichan.inu_todo.model.RetrofitBuilder
import dev.kichan.inu_todo.model.data.member.SignUpReq
import dev.kichan.inu_todo.model.service.MemberService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Preview(showBackground = true)
@Composable
fun SignUpPage(navController: NavController = rememberNavController()) {
    val id = remember { mutableStateOf("") }
    val pass = remember { mutableStateOf("") }

    val signUp: (String, String) -> Unit = { id, pass ->
        CoroutineScope(Dispatchers.IO).launch {
            val service = RetrofitBuilder.getService(MemberService::class.java)

            val result = service.signUp(
                body = SignUpReq(
                    userId = id,
                    userPw = pass,
                    confirmUserPw = pass
                )
            )

            if (result.isSuccessful) {
                MainActivity.user = result.body()!!
                withContext(Dispatchers.Main) {
                    navController.navigate(Page.Home.name)
                    navController.clearBackStack(Page.SIGN_UP.name)
                    navController.clearBackStack(Page.MAIN.name)
                }
                Log.d("SignUp", "성공")
            } else {
                Log.d("SignUp", "실패")
            }

            Log.d("SignUp", result.body().toString())
        }
    }

    Column {
        TextField(value = id.value, onValueChange = { id.value = it })
        TextField(value = pass.value, onValueChange = { pass.value = it })

        Button(onClick = { signUp(id.value, pass.value) }) {
            Text(text = "회원가입")
        }
    }
}