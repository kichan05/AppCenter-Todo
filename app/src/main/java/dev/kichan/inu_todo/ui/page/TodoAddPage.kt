package dev.kichan.inu_todo.ui.page

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.inu_todo.MainActivity
import dev.kichan.inu_todo.model.RetrofitBuilder
import dev.kichan.inu_todo.model.data.category.Category
import dev.kichan.inu_todo.model.data.todo.TodoCreateReq
import dev.kichan.inu_todo.model.service.CategoryService
import dev.kichan.inu_todo.model.service.TodoService
import dev.kichan.inu_todo.ui.component.CategoryItem
import dev.kichan.inu_todo.ui.component.Input
import dev.kichan.inu_todo.ui.component.InuButton
import dev.kichan.inu_todo.ui.theme.INUTodoTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun TodoAddPage(navController: NavController) {
    val todoInput = remember { mutableStateOf("") }
    val categoryInput = remember { mutableStateOf<Category?>(null) }
    val categoryList = remember { mutableStateOf<List<Category>>(listOf()) }

    val getData = {
        val categoryService = RetrofitBuilder.getService(CategoryService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val categoryRes = categoryService.getUserCategory(MainActivity.user.memberId)
            if (categoryRes.isSuccessful) {
                withContext(Dispatchers.Main) {
                    categoryList.value = categoryRes.body()!!
                }
            }
        }
    }

    val todoCreate: (String, Category) -> Unit = { todoName, category ->
        val service = RetrofitBuilder.getService(TodoService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val result = service.todoCreate(
                memberId = MainActivity.user.memberId,
                body = TodoCreateReq(
                    category = category,
                    content = todoName,
                    setDate = "2024-05-22",
                    writeDate = "2024-05-22"
                ),
            )

            if (result.isSuccessful) {
                withContext(Dispatchers.Main) {
                    navController.popBackStack()
                }
            } else {
                Log.d("Todo", "실패 ${result.errorBody()}")
            }
        }
    }

    getData()

    Column(
        Modifier.fillMaxSize()
    ) {
        Input(
            value = todoInput.value,
            onChange = { todoInput.value = it },
            placeholder = "할일을 입력해주세요",
            modifier = Modifier.fillMaxWidth(),
        )
        Column {
            categoryList.value.map {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = categoryInput.value == it,
                        onClick = { categoryInput.value = it }
                    )
                    Text(text = it.content)
                }
            }
        }

        InuButton(
            onClick = {
                todoCreate(todoInput.value, categoryInput.value!!)
            },
            text = "할일 추가"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TodoAddPagePreview() {
    INUTodoTheme {
        TodoAddPage(navController = rememberNavController())
    }
}