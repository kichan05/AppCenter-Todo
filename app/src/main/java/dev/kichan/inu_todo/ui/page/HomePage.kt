package dev.kichan.inu_todo.ui.page

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.inu_todo.MainActivity
import dev.kichan.inu_todo.model.RetrofitBuilder
import dev.kichan.inu_todo.model.data.todo.Todo
import dev.kichan.inu_todo.model.data.todo.TodoCreateReq
import dev.kichan.inu_todo.model.service.TodoService
import dev.kichan.inu_todo.ui.component.Header
import dev.kichan.inu_todo.ui.component.InuButton
import dev.kichan.inu_todo.ui.component.TodoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Preview
@Composable
fun HomePage(navController: NavController = rememberNavController()) {
    val todoList = rememberSaveable { mutableStateOf<List<Todo>>(listOf()) }
    val input = remember { mutableStateOf("") }

    val getTodo = {
        val service = RetrofitBuilder.getService(TodoService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val res = service.getTodo(MainActivity.user.memberId)
            if (res.isSuccessful) {
                todoList.value = res.body()!!
            }
        }
    }

    val todoCreate: (String) -> Unit = {
        val service = RetrofitBuilder.getService(TodoService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val result = service.todoCreate(
                memberId = MainActivity.user.memberId,
                body = TodoCreateReq(
                    category = "Test",
                    content = it,
                    setDate = "2024-05-22",
                    writeDate = "2024-05-22"
                ),
            )

            if (result.isSuccessful) {
                Log.d("Todo", "성공")
            } else {
                Log.d("Todo", "실패 ${result.errorBody()}")
            }
        }
    }

    Column {
        Header(title = "회원가입") {  }
        Text(text = MainActivity.user.toString())

        LazyColumn {
            items(todoList.value) {
                TodoItem(todo = it)
            }
        }

        InuButton(onClick = { getTodo() }, text = "투두 가져오기")

        Row {
            TextField(value = input.value, onValueChange = { input.value = it })
            InuButton(onClick = { todoCreate(input.value) }, text = "투두 생성")
        }
    }
}