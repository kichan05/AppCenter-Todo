package dev.kichan.inu_todo

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.kichan.inu_todo.ui.page.MainPage
import dev.kichan.inu_todo.ui.page.Page
import dev.kichan.inu_todo.ui.page.SignInPage
import dev.kichan.inu_todo.ui.page.SignUpPage
import dev.kichan.inu_todo.ui.theme.INUTodoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold {
                MyApp(Modifier.fillMaxSize().padding(it))
            }
        }
    }

    companion object {
        @Composable
        fun getContext() : Context {
            return LocalContext.current
        }

        @Composable
        fun showToast(text: String) {
            Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show()
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
        }
    }
}