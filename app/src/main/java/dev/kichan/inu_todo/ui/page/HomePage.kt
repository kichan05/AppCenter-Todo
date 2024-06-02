package dev.kichan.inu_todo.ui.page

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.inu_todo.MainActivity
import dev.kichan.inu_todo.R
import dev.kichan.inu_todo.model.RetrofitBuilder
import dev.kichan.inu_todo.model.data.category.Category
import dev.kichan.inu_todo.model.data.todo.Todo
import dev.kichan.inu_todo.model.data.todo.TodoCreateReq
import dev.kichan.inu_todo.model.service.CategoryService
import dev.kichan.inu_todo.model.service.TodoService
import dev.kichan.inu_todo.ui.component.CategoryItem
import dev.kichan.inu_todo.ui.component.HomeHeader
import dev.kichan.inu_todo.ui.component.InuButton
import dev.kichan.inu_todo.ui.component.TodoItem
import dev.kichan.inu_todo.ui.theme.INUTodoTheme
import dev.kichan.inu_todo.ui.theme.suit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun HomePage(navController: NavController) {
    val input = remember { mutableStateOf("") }
    val todoList = rememberSaveable { mutableStateOf<List<Todo>>(listOf()) }
    val categoryList = remember { mutableStateOf<List<Category>>(listOf()) }

    val getTodo = {
        val service = RetrofitBuilder.getService(TodoService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val res = service.getTodo(MainActivity.user.memberId)
            if (res.isSuccessful) {
                todoList.value = res.body()!!
            }
        }
    }

    val getCategory = {
        val categoryService = RetrofitBuilder.getService(CategoryService::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val res = categoryService.getUserCategory(MainActivity.user.memberId)

            if (res.isSuccessful) {
                categoryList.value = res.body()!!
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

//    getTodo()
//    getCategory()

    Column(Modifier.fillMaxSize()) {
        HomeHeader()
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "5월",
                style = TextStyle(
                    color = Color(0xff553910),
                    fontFamily = suit,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp,
                )
            )

            Image(
                painter = painterResource(id = R.drawable.ic_calendar_days),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 7.dp)
                    .shadow(3.dp, RoundedCornerShape(100.dp))
                    .background(Color.White)
                    .padding(6.dp)
            )

            LazyRow(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(categoryList.value) {
                    CategoryItem(category = it)
                }
                item {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                            .background(Color(0xfff5f5f5), RoundedCornerShape(100.dp))
                            .clickable { navController.navigate(Page.Category.name) },
                        tint = Color(0xffA2A2A2)
                    )
                }
            }
        }

        LazyColumn {
            items(todoList.value) {
                TodoItem(todo = it)
            }
        }

        InuButton(onClick = { getTodo() }, text = "투두 가져오기")
        InuButton(onClick = { getCategory() }, text = "카테고리 가져오기")
        InuButton(onClick = { navController.navigate(Page.Category.name) }, text = "카테고리 추가")

        Row {
            TextField(value = input.value, onValueChange = { input.value = it })
            InuButton(onClick = { todoCreate(input.value) }, text = "투두 생성")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    INUTodoTheme {
        HomePage(rememberNavController())
    }
}