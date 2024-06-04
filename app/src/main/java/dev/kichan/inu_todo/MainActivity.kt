package dev.kichan.inu_todo

import android.os.Bundle
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
import dev.kichan.inu_todo.model.data.member.User
import dev.kichan.inu_todo.ui.page.CategoryPage
import dev.kichan.inu_todo.ui.page.HomePage
import dev.kichan.inu_todo.ui.page.MainPage
import dev.kichan.inu_todo.ui.page.Page
import dev.kichan.inu_todo.ui.page.SignInPage
import dev.kichan.inu_todo.ui.page.SignUpPage
import dev.kichan.inu_todo.ui.page.TodoAddPage
import dev.kichan.inu_todo.ui.theme.INUTodoTheme

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
        lateinit var user: User
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
                CategoryPage(navController)
            }
            composable(route = Page.TODO_ADD.name) {
                TodoAddPage(navController = navController)
            }
        }
    }
}