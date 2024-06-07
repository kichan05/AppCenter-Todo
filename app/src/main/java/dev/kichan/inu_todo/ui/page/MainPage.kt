package dev.kichan.inu_todo.ui.page

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import dev.kichan.inu_todo.model.data.member.SignInReq
import dev.kichan.inu_todo.model.service.MemberService
import dev.kichan.inu_todo.ui.component.InuButton
import dev.kichan.inu_todo.ui.theme.Blue_400
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun MainPage(navController: NavController = rememberNavController()) {
    val imageIdList = listOf<Int>(R.drawable.main_image_1, R.drawable.main_image_2, R.drawable.main_image_3)
    val mainImage = remember {
        imageIdList.random()
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 17.dp, vertical = 38.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            Modifier.padding(top = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painterResource(id = mainImage),
                contentDescription = null,
                modifier = Modifier
                    .width(180.dp)
                    .height(180.dp)
            )

            Image(painter = painterResource(id = R.drawable.logo), contentDescription = null)
            Text(
                text = "나만의 Todo list로 하루하루 알차게!",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.padding(top = 16.dp)
            )
        }


        Column {
            InuButton(
                onClick = { navController.navigate(Page.SIGN_IN.name) },
                modifier = Modifier
                    .fillMaxWidth(),
                text = "로그인",
            )

            InuButton(
                onClick = { navController.navigate(Page.SIGN_UP.name) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 14.dp),
                text = "회원가입",
                backgroundColor = Color.White,
                textColor = Blue_400,
                border = BorderStroke(0.8.dp, Blue_400)
            )
        }
    }
}