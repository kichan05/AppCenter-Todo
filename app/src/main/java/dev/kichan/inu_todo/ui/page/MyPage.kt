package dev.kichan.inu_todo.ui.page

import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
import dev.kichan.inu_todo.model.service.MemberService
import dev.kichan.inu_todo.ui.component.WithdrawalDialog
import dev.kichan.inu_todo.ui.component.Header
import dev.kichan.inu_todo.ui.theme.Blue_100
import dev.kichan.inu_todo.ui.theme.Blue_200
import dev.kichan.inu_todo.ui.theme.Blue_400
import dev.kichan.inu_todo.ui.theme.INUTodoTheme
import dev.kichan.inu_todo.ui.theme.suit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun MyPage(navController: NavHostController) {
    val isShowWithdrawalDialog = remember { mutableStateOf(false) }

    val withdrawal: () -> Unit = {
        val memberService = RetrofitBuilder.getService(MemberService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val res = memberService.delete(MainActivity.token)
            if (res.isSuccessful) {
                withContext(Dispatchers.Main) {
                    MainActivity.token = ""
                    isShowWithdrawalDialog.value = false

                    navController.navigate(Page.MAIN.name) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    val logout: () -> Unit = {
        MainActivity.token = ""

        navController.navigate(Page.MAIN.name) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }

    val changePassword: () -> Unit = {
        navController.navigate(Page.CHANGE_PASSWORD.name)
    }

    Column(
        Modifier.fillMaxSize(),
    ) {
        Header(title = "마이페이지") { navController.popBackStack() }

        Column(
            Modifier
                .fillMaxSize()
                .padding(vertical = 37.dp, horizontal = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            val shape = RoundedCornerShape(12.dp)

            Column(
                modifier = Modifier
                    .shadow(3.dp, shape)
                    .background(Color.White, shape)
                    .padding(horizontal = 12.dp, vertical = 15.dp),

                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                Image(
                    painter = painterResource(id = R.drawable.heechan),
                    contentDescription = null,
                    modifier = Modifier
                        .width(146.dp)
                        .height(146.dp)
                        .clip(CircleShape),
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "안녕하세요. ckstmznf님!",
                    style = TextStyle(
                        fontFamily = suit,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    ),
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    Modifier
                        .fillMaxWidth()
                        .clip(shape)
                ) {
                    val modifier = Modifier
                        .weight(1.0f)
                        .height(67.dp)
                        .background(Blue_200)

                    val fontStyle = TextStyle(
                        color = Blue_400,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                    )

                    Box(
                        modifier.clickable { logout() },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "로그아웃",
                            style = fontStyle,
                            textAlign = TextAlign.Center
                        )
                    }
                    Box(
                        modifier.clickable { changePassword() },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "비민번호 변경",
                            style = fontStyle,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Surface(
                color = Blue_100,
                shape = shape,
                modifier = Modifier.clickable {
                    isShowWithdrawalDialog.value = true
                }
            ) {
                Text(
                    text = "탈퇴하기",
                    Modifier.padding(vertical = 9.dp, horizontal = 23.dp),
                    style = TextStyle(
                        color = Blue_400,
                        fontFamily = suit,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }

    if (isShowWithdrawalDialog.value) {
        WithdrawalDialog(
            onDismiss = { isShowWithdrawalDialog.value = false },
            onWithdrawal = withdrawal,
            withdrawalText = "탈퇴하기",
            image = R.drawable.boom
        ) {
            Text(
                text = "ckstmznf님,\n정말 계정을 삭제하실건가요?",
                fontFamily = suit,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyPagePreview() {
    INUTodoTheme {
        MyPage(navController = rememberNavController())
    }
}