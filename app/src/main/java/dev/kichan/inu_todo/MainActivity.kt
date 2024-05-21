package dev.kichan.inu_todo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import dev.kichan.inu_todo.model.RetrofitBuilder
import dev.kichan.inu_todo.model.data.member.SignInReq
import dev.kichan.inu_todo.model.data.member.SignUpReq
import dev.kichan.inu_todo.model.service.MemberService
import dev.kichan.inu_todo.ui.theme.INUTodoTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    val signUp : (String, String) -> Unit = {id, pass ->
        CoroutineScope(Dispatchers.IO).launch {
            val service = RetrofitBuilder.getService(MemberService::class.java)

            val result = service.signUp(
                body = SignUpReq(
                    userId = id,
                    userPw = pass,
                    confirmUserPw = pass
                )
            )

            if(result.isSuccessful) {
                Log.d("SignUp", "성공")
            }
            else {
                Log.d("SignUp", "실패")
            }

            Log.d("SignUp", result.body().toString())
        }
    }

    val signIn : (String, String) -> Unit = {id, pass ->
        CoroutineScope(Dispatchers.IO).launch {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val id = remember{ mutableStateOf("") }
            val pass = remember{ mutableStateOf("") }

            INUTodoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        TextField(value = id.value, onValueChange = { id.value = it })
                        TextField(value = pass.value, onValueChange = { pass.value = it })

                        Button(onClick = { signIn(id.value, pass.value) }) {
                            Text(text = "로그인")
                        }
                    }
                }
            }
        }
    }
}