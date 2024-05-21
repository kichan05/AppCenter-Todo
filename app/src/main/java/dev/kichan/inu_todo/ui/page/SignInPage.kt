package dev.kichan.inu_todo.ui.page

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import dev.kichan.inu_todo.model.RetrofitBuilder
import dev.kichan.inu_todo.model.data.member.SignInReq
import dev.kichan.inu_todo.model.service.MemberService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun SignInPage() {
    val id = remember { mutableStateOf("") }
    val pass = remember { mutableStateOf("") }

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
                Log.d("SignIn", "성공")
            }
            else {
                Log.d("SignIn", "실패")
            }

            Log.d("SignIn", result.body().toString())
        }
    }
}