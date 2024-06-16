package dev.kichan.inu_todo.ui.page

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import dev.kichan.inu_todo.model.data.todo.TodoCreateReq
import dev.kichan.inu_todo.model.service.CategoryService
import dev.kichan.inu_todo.model.service.TodoService
import dev.kichan.inu_todo.ui.component.CategoryItem
import dev.kichan.inu_todo.ui.component.DatePicker
import dev.kichan.inu_todo.ui.component.Header
import dev.kichan.inu_todo.ui.component.Input
import dev.kichan.inu_todo.ui.component.InuButton
import dev.kichan.inu_todo.ui.theme.Gray_200
import dev.kichan.inu_todo.ui.theme.INUTodoTheme
import dev.kichan.inu_todo.ui.theme.suit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TodoAddPage(navController: NavController) {
    val todoInput = remember { mutableStateOf("") }
    val selectCategory = remember { mutableStateOf<Category?>(null) }
    val categoryList = remember {
        mutableStateOf<List<Category>>(
            listOf(
//                Category(categoryId = 6799, content = "postulant", color = "ff0000")
            )
        )
    }

    val isOpenDatePicker = remember { mutableStateOf(false) }
    val selectDate = remember { mutableStateOf(LocalDate.now()) }

    val getData = {
        val categoryService = RetrofitBuilder.getService(CategoryService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val categoryRes = categoryService.getUserCategory(MainActivity.token)
            if (categoryRes.isSuccessful) {
                withContext(Dispatchers.Main) {
                    categoryList.value = categoryRes.body()!!
                }
            }
        }
    }

    val todoCreate: (String, Category, LocalDate) -> Unit = { todoName, category, selectDate ->
        val service = RetrofitBuilder.getService(TodoService::class.java)

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        CoroutineScope(Dispatchers.IO).launch {
            val result = service.todoCreate(
                authorization = MainActivity.token,
                body = TodoCreateReq(
                    category = category,
                    content = todoName,
                    setDate = selectDate.format(formatter),
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
        Header(title = "Todo") { navController.popBackStack() }
        Column(
            Modifier
                .fillMaxSize()
                .padding(13.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {

                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "${selectDate.value.monthValue}월 ${selectDate.value.dayOfMonth}일",
                        style = TextStyle(
                            color = Color(0xff553910),
                            fontFamily = suit,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 25.sp,
                        ),
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Image(
                        painter = painterResource(id = R.drawable.ic_calendar_days),
                        contentDescription = null,
                        modifier = Modifier
                            .shadow(3.dp, RoundedCornerShape(100.dp))
                            .background(Color.White)
                            .clickable { isOpenDatePicker.value = true }
                            .padding(6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(17.dp))

                Input(
                    value = todoInput.value,
                    onChange = { todoInput.value = it },
                    placeholder = "Todo를 입력해주세요",
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(17.dp))

                Column(
                    Modifier.padding(start = 14.dp)
                ) {
                    Text(
                        text = "카테고리",
                        style = TextStyle(
                            color = Color(0xff232323),
                            fontFamily = suit,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                        )
                    )
                    Text(
                        text = "To do의 카테고리를 선택해주세요!",
                        style = TextStyle(
                            color = Color(0xff8c8c8c),
                            fontFamily = suit,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                        )
                    )
                }

                Spacer(modifier = Modifier.height(22.dp))

                val shape = RoundedCornerShape(12.dp)

                LazyRow(
                    contentPadding = PaddingValues(vertical = 37.dp, horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, shape)
                        .border(1.dp, Gray_200, shape)
                        .shadow(1.dp, shape)
                ) {
                    items(categoryList.value) {
                        CategoryItem(
                            category = it,
                            modifier = Modifier.clickable { selectCategory.value = it },
                            isEnable = selectCategory.value == it
                        )
                    }
                }
            }

            InuButton(
                onClick = {
                    todoCreate(todoInput.value, selectCategory.value!!, selectDate.value!!)
                },
                text = "확인",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    if (isOpenDatePicker.value) {
        DatePicker(
            selectDay = selectDate.value,
            todoList = listOf(),
            onSelect = { selectDate.value = it },
            onDismiss = { isOpenDatePicker.value = false }
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