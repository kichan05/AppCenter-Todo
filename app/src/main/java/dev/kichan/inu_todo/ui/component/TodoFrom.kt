package dev.kichan.inu_todo.ui.component

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.inu_todo.MainActivity
import dev.kichan.inu_todo.R
import dev.kichan.inu_todo.model.RetrofitBuilder
import dev.kichan.inu_todo.model.data.category.Category
import dev.kichan.inu_todo.model.data.todo.Todo
import dev.kichan.inu_todo.model.data.todo.TodoCreateReq
import dev.kichan.inu_todo.model.service.CategoryService
import dev.kichan.inu_todo.ui.theme.Gray_200
import dev.kichan.inu_todo.ui.theme.suit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TodoFrom(todo: Todo?, onFeatch: (TodoCreateReq) -> Unit) {
    val todoInput = remember { mutableStateOf(todo?.content ?: "") }
    val selectCategoryId = remember { mutableStateOf<Int?>(todo?.category?.categoryId) }
    val selectDate = remember { mutableStateOf(LocalDate.now()) }

    val categoryList = remember {
        mutableStateOf<List<Category>>(
            listOf(
//                Category(categoryId = 6799, content = "postulant", color = "ff0000")
            )
        )
    }

    val isOpenDatePicker = remember { mutableStateOf(false) }


    val getCategory = {
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

    getCategory()

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
                        modifier = Modifier.clickable { selectCategoryId.value = it.categoryId },
                        isEnable = selectCategoryId.value == it.categoryId
                    )
                }
            }
        }

        InuButton(
            onClick = {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val formattedDate = selectDate.value.format(formatter)

                onFeatch(
                    TodoCreateReq(
                        categoryId = selectCategoryId.value!!,
                        content = todoInput.value,
                        setDate = formattedDate
                    )
                )
            },
            text = "확인",
            modifier = Modifier.fillMaxWidth()
        )
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