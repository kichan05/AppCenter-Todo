package dev.kichan.inu_todo.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.kichan.inu_todo.MainActivity
import dev.kichan.inu_todo.model.RetrofitBuilder
import dev.kichan.inu_todo.model.data.category.Category
import dev.kichan.inu_todo.model.data.todo.Todo
import dev.kichan.inu_todo.model.data.todo.TodoCreateReq
import dev.kichan.inu_todo.model.service.TodoService
import dev.kichan.inu_todo.ui.component.Header
import dev.kichan.inu_todo.ui.component.TodoFrom
import dev.kichan.inu_todo.ui.theme.INUTodoTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun TodoEditPage(navController: NavHostController, todo: Todo) {
    val onFetch: (TodoCreateReq) -> Unit = {
        val service = RetrofitBuilder.getService(TodoService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val res = service.editTodo(
                authorization = MainActivity.token,
                todoId = todo.todoId,
                body = it
            )

            if(res.isSuccessful) {
                withContext(Dispatchers.Main) {
                    navController.popBackStack()
                }
            }
        }
    }
    Column {
        Header(title = "Todo 수정") { navController.popBackStack() }

        TodoFrom(todo = todo, onFeatch = onFetch)
    }
}

@Preview(showBackground = true)
@Composable
fun TodoEditPagePreview() {
    INUTodoTheme {
        TodoEditPage(
            navController = rememberNavController(),
            todo = Todo(
                todoId = 2161, category = Category(
                    categoryId = 3677,
                    content = "commodo",
                    color = "bibendum"
                ), checked = false, content = "intellegat", _setDate = "melius"
            )
        )
    }
}