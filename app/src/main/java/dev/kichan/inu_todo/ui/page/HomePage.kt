package dev.kichan.inu_todo.ui.page

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.inu_todo.MainActivity

@Preview
@Composable
fun HomePage(navController: NavController = rememberNavController()) {
    Log.d("UserData", MainActivity.user.toString())
    Text(text = MainActivity.user.toString())
}