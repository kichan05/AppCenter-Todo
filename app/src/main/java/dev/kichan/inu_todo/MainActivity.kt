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
import com.google.gson.Gson
import com.google.gson.internal.GsonBuildConfig
import dev.kichan.inu_todo.model.data.member.SignUpReq
import dev.kichan.inu_todo.model.service.MemberService
import dev.kichan.inu_todo.ui.theme.INUTodoTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : ComponentActivity() {
    var retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://na2ru2.me:5151")
        .build()

    var service: MemberService = retrofit.create(MemberService::class.java)

    val signUp : (String, String) -> Unit = {id, pass ->
        CoroutineScope(Dispatchers.IO).launch {
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

                        Button(onClick = { signUp(id.value, pass.value) }) {
                            Text(text = "회원가입")
                        }
                    }
                }
            }
        }
    }
}