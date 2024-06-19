package dev.kichan.inu_todo.ui.page

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.kichan.inu_todo.MainActivity
import dev.kichan.inu_todo.model.RetrofitBuilder
import dev.kichan.inu_todo.model.data.category.Category
import dev.kichan.inu_todo.model.data.category.CreateCategoryReq
import dev.kichan.inu_todo.model.service.CategoryService
import dev.kichan.inu_todo.ui.component.CategoryFrom
import dev.kichan.inu_todo.ui.component.Header
import dev.kichan.inu_todo.ui.component.InuButton
import dev.kichan.inu_todo.ui.theme.INUTodoTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CategoryAddPage(navController: NavHostController) {
    val addCategory: (CreateCategoryReq) -> Unit = {
        Log.d("category", it.toString())
        val categoryService = RetrofitBuilder.getService(CategoryService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val res = categoryService.createCategory(
                authorization = MainActivity.token,
                body = it
            )

            withContext(Dispatchers.Main) {
                if (res.isSuccessful) {
                    navController.popBackStack()
                } else {
                    Log.d("Category", "싱패")
                }
            }
        }
    }
    val category = remember { mutableStateOf(CreateCategoryReq(content = "", color = "")) }

    Column(Modifier.fillMaxSize()) {
        Header(title = "카테고리") { navController.popBackStack() }
        Column(
            Modifier
                .padding(horizontal = 13.dp, vertical = 38.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            CategoryFrom(category = category.value, onChange = {category.value = it})

            InuButton(
                onClick = {
                    addCategory(category.value)
                },
                text = "저장하기", modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryPagePreview() {
    INUTodoTheme {
        CategoryAddPage(navController = rememberNavController())
    }
}