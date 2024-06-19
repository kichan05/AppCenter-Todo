package dev.kichan.inu_todo.ui.page

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.kichan.inu_todo.MainActivity
import dev.kichan.inu_todo.R
import dev.kichan.inu_todo.model.RetrofitBuilder
import dev.kichan.inu_todo.model.data.category.Category
import dev.kichan.inu_todo.model.data.category.CreateCategoryReq
import dev.kichan.inu_todo.model.service.CategoryService
import dev.kichan.inu_todo.ui.component.CategoryFrom
import dev.kichan.inu_todo.ui.component.Header
import dev.kichan.inu_todo.ui.component.InuButton
import dev.kichan.inu_todo.ui.component.WithdrawalDialog
import dev.kichan.inu_todo.ui.theme.INUTodoTheme
import dev.kichan.inu_todo.ui.theme.suit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CategoryEditPage(navController: NavHostController, category: Category) {
    val isCategoryDeleteDialogOpen = remember { mutableStateOf(false) }
    val editCategory: (CreateCategoryReq) -> Unit = {
        val categoryService = RetrofitBuilder.getService(CategoryService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val res = categoryService.editCategory(
                authorization = MainActivity.token,
                body = it,
                categoryId = category.categoryId,
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

    val onDeleteOpen: () -> Unit = {
        isCategoryDeleteDialogOpen.value = true
    }

    val onDelete: () -> Unit = {
        val categoryService = RetrofitBuilder.getService(CategoryService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val res = categoryService.deleteCategory(
                authorization = MainActivity.token,
                categoryId = category.categoryId
            )

            if(res.isSuccessful) {
                withContext(Dispatchers.Main) {
                    isCategoryDeleteDialogOpen.value = false
                    navController.popBackStack()
                }
            }
        }
    }

    val newCategory = remember {
        mutableStateOf(
            CreateCategoryReq(
                content = category.content,
                color = category.color
            )
        )
    }

    Column(Modifier.fillMaxSize()) {
        Header(title = "카테고리") { navController.popBackStack() }
        Column(
            Modifier
                .padding(horizontal = 13.dp, vertical = 38.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            CategoryFrom(category = newCategory.value, onChange = { newCategory.value = it })

            Row(
                Modifier.fillMaxWidth(),
            ) {
                InuButton(
                    onClick = { onDeleteOpen() },
                    text = "삭제",
                    Modifier.weight(1f),
                    textColor = Color(0xffa2a2a2),
                    backgroundColor = Color.White,
                    border = BorderStroke(1.dp, Color(0xffa2a2a2))
                )
                Spacer(modifier = Modifier.width(9.dp))
                InuButton(
                    onClick = {
                        editCategory(newCategory.value)
                    },
                    text = "저장", modifier = Modifier.weight(1f)
                )
            }
        }
    }

    if (isCategoryDeleteDialogOpen.value) {
        WithdrawalDialog(
            onDismiss = { isCategoryDeleteDialogOpen.value = false },
            onWithdrawal = onDelete,
            withdrawalText = "삭제하기",
            image = R.drawable.x
        ) {
            Text(
                text = "\"${category.content}\"", style = TextStyle(
                    fontFamily = suit,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            )
            Text(
                text = "카테고리를 삭제하시겠습니까?\n" +
                        "이 카테고리로 등록되어있는\n" +
                        "할 일이 모두 삭제됩니다.",
                style = TextStyle(
                    fontFamily = suit,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryEditPagePreview() {
    INUTodoTheme {
        CategoryEditPage(
            rememberNavController(), Category(
                categoryId = 3037,
                content = "tation",
                color = "turpis"
            )
        )
    }
}