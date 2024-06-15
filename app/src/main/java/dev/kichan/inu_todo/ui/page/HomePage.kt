package dev.kichan.inu_todo.ui.page

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import dev.kichan.inu_todo.model.service.CategoryService
import dev.kichan.inu_todo.model.service.TodoService
import dev.kichan.inu_todo.ui.component.CategoryItem
import dev.kichan.inu_todo.ui.component.HomeHeader
import dev.kichan.inu_todo.ui.component.TodoItem
import dev.kichan.inu_todo.ui.theme.INUTodoTheme
import dev.kichan.inu_todo.ui.theme.suit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun HomePage(navController: NavController) {
    val todoService = RetrofitBuilder.getService(TodoService::class.java)
    val categoryService = RetrofitBuilder.getService(CategoryService::class.java)

    val todoList = rememberSaveable { mutableStateOf<List<Todo>>(listOf()) }
    val categoryList = remember { mutableStateOf<List<Category>>(listOf()) }

    val getData = {

        CoroutineScope(Dispatchers.IO).launch {
            val todoRes = todoService.getTodo(MainActivity.user.memberId)
            val categoryRes = categoryService.getUserCategory(MainActivity.user.memberId)

            if (todoRes.isSuccessful) {
                withContext(Dispatchers.Main) {
                    todoList.value = todoRes.body()!!
                }
            }

            if (categoryRes.isSuccessful) {
                withContext(Dispatchers.Main) {
                    categoryList.value = categoryRes.body()!!
                }
            }
        }
    }

    val checkTodo : (Todo) -> Unit = {
        CoroutineScope(Dispatchers.IO).launch {
            val res = todoService.editTodo(
                it.todoId,
                it.copy(checked = !it.checked)
            )

            if(res.isSuccessful) {
                getData()
            }
            else {
                Log.d("TodoCheck", "실패")
            }
        }
    }

    getData()

    Column(Modifier.fillMaxSize()) {
        HomeHeader(onGoMyPage = { navController.navigate(Page.MY.name) })
        Column(Modifier.padding(horizontal = 13.dp)) {
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
                    ),
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

            val shape = RoundedCornerShape(12.dp)

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp)
                    .shadow(3.dp, shape)
                    .background(Color.White, shape)
                    .padding(vertical = 20.dp, horizontal = 15.dp)
            ) {
                Text(
                    text = "15일",
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = suit,
                    ),
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(11.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    items(todoList.value) {
                        TodoItem(todo = it, onClick = checkTodo)
                    }
                }
            }

            Row(
                Modifier
                    .padding(top = 27.dp)
                    .fillMaxWidth()
                    .clickable { navController.navigate(Page.TODO_ADD.name) }
                    .background(Color(0xfff5f5f5), RoundedCornerShape(12.dp))
                    .padding(vertical = 21.dp),

                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = null,
                    tint = Color(0xffa2a2a2)
                )
                Text(
                    text = "Todolis 추가",
                    style = TextStyle(
                        fontFamily = suit,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                )
            }
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