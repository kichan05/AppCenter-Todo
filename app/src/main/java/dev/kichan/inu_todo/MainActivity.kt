package dev.kichan.inu_todo

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.kichan.inu_todo.model.data.category.Category
import dev.kichan.inu_todo.model.data.todo.Todo
import dev.kichan.inu_todo.ui.page.CategoryAddPage
import dev.kichan.inu_todo.ui.page.CategoryEditPage
import dev.kichan.inu_todo.ui.page.ChangePasswordPage
import dev.kichan.inu_todo.ui.page.HomePage
import dev.kichan.inu_todo.ui.page.MainPage
import dev.kichan.inu_todo.ui.page.MyPage
import dev.kichan.inu_todo.ui.page.Page
import dev.kichan.inu_todo.ui.page.SignInPage
import dev.kichan.inu_todo.ui.page.SignUpPage
import dev.kichan.inu_todo.ui.page.TodoAddPage
import dev.kichan.inu_todo.ui.page.TodoEditPage
import dev.kichan.inu_todo.ui.theme.INUTodoTheme
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold {
                MyApp(
                    Modifier
                        .fillMaxSize()
                        .padding(it))
            }
        }
    }

    companion object {
        lateinit var token: String
        fun showToast(context : Context, message : String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyApp(modifier: Modifier = Modifier) {
    INUTodoTheme {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = Page.MAIN.name) {
            composable(route = Page.MAIN.name) {
                MainPage(navController)
            }
            composable(route = Page.SIGN_IN.name) {
                SignInPage(navController)
            }
            composable(route = Page.SIGN_UP.name) {
                SignUpPage(navController)
            }
            composable(route = Page.Home.name) {
                HomePage(navController)
            }
            composable(route = Page.Category.name) {
                CategoryAddPage(navController)
            }
            composable(route = Page.TODO_ADD.name) {
                TodoAddPage(navController = navController)
            }
            composable(route = "${Page.TODO_EDIT.name}/{todoJson}") {back ->
                val todoJson = back.arguments?.getString("todoJson")
                val todo = Gson().fromJson(todoJson, Todo::class.java)
                TodoEditPage(navController = navController, todo = todo)
            }
            composable(route = Page.MY.name) {
                MyPage(navController = navController)
            }
            composable(route = Page.CHANGE_PASSWORD.name) {
                ChangePasswordPage(navController = navController)
            }
            composable(route = "${Page.CATEGODY_EDIT.name}/{categoryJson}") {
                val todoJson = it.arguments?.getString("categoryJson")
                val category = Gson().fromJson(todoJson, Category::class.java)
                CategoryEditPage(navController = navController, category)
            }
        }
    }
}