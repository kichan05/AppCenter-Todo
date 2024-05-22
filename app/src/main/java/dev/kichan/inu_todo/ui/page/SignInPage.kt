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
import dev.kichan.inu_todo.model.RetrofitBuilder
import dev.kichan.inu_todo.model.data.member.SignInReq
import dev.kichan.inu_todo.model.data.todo.TodoCreateReq
import dev.kichan.inu_todo.model.service.MemberService
import dev.kichan.inu_todo.model.service.TodoService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun SignInPage(navController: NavController = rememberNavController()) {
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

    val todoCreate : (String) -> Unit = {
        val service = RetrofitBuilder.getService(TodoService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val result = service.todoCreate(
                memberId = id.value,
                body = TodoCreateReq(
                    category = "Test",
                    content = pass.value,
                    setDate = "2024-05-22",
                    writeDate = "2024-05-22"
                ), )

            if(result.isSuccessful)
                Log.d("Todo", "성공")
            else
                Log.d("Todo", "실패 ${result.errorBody()}")
        }
    }
    
    Column {
        TextField(value = id.value, onValueChange = { id.value = it })
        TextField(value = pass.value, onValueChange = { pass.value = it })

        Button(onClick = { signIn(id.value, pass.value) }) {
            Text(text = "로그인")
        }

        Button(onClick = { todoCreate(pass.value) }) {
            Text(text = "투두 생성")
        }
    }
}