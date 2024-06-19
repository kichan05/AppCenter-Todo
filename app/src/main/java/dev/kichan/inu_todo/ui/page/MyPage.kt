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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.kichan.inu_todo.MainActivity
import dev.kichan.inu_todo.R
import dev.kichan.inu_todo.model.RetrofitBuilder
import dev.kichan.inu_todo.model.service.MemberService
import dev.kichan.inu_todo.ui.component.Header
import dev.kichan.inu_todo.ui.theme.Blue_100
import dev.kichan.inu_todo.ui.theme.Blue_200
import dev.kichan.inu_todo.ui.theme.Blue_400
import dev.kichan.inu_todo.ui.theme.Gray_200
import dev.kichan.inu_todo.ui.theme.Gray_300
import dev.kichan.inu_todo.ui.theme.INUTodoTheme
import dev.kichan.inu_todo.ui.theme.Red_300
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
            val res = memberService.delete(MainActivity.token, "qwer1234!")
            if (res.isSuccessful) {
                withContext(Dispatchers.Main) {
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
                    .background(Color.White, shape)
                    .shadow(1.dp, shape)
                    .padding(vertical = 11.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.heechan),
                    contentDescription = null,
                    modifier = Modifier
                        .width(146.dp)
                        .height(146.dp)
                        .clip(CircleShape),
                )

                Text(
                    text = "안녕하세요. 박희찬님!",
                    style = TextStyle(
                        fontFamily = suit,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier.padding(top = 24.dp)
                )

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 39.dp, start = 11.dp, end = 11.dp)
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
        WithdrawalDialog({ isShowWithdrawalDialog.value = false }, onWithdrawal = withdrawal)
    }
}

@Preview(showBackground = true)
@Composable
fun MyPagePreview() {
    INUTodoTheme {
        MyPage(navController = rememberNavController())
    }
}

@Preview
@Composable
fun WithdrawalDialog(
    onDismiss: () -> Unit = {},
    onWithdrawal: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(45.dp))
                Text(
                    text = "김사랑님,\n정말 계정을 삭제하실건가요?",
                    fontFamily = suit,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(15.dp))
                Image(
                    painter = painterResource(id = R.drawable.boom),
                    modifier = Modifier
                        .width(126.dp)
                        .height(126.dp),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .shadow(1.dp)
                ) {
                    val modifier = Modifier
                        .weight(1.0f)
                        .height(62.dp)

                    val fontStyle = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontFamily = suit,
                        fontSize = 16.sp,
                    )

                    Box(modifier.clickable { onDismiss() }, contentAlignment = Alignment.Center) {
                        Text(
                            text = "취소",
                            style = fontStyle.copy(color = Gray_300),
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .width(1.5.dp)
                            .height(38.dp)
                            .background(Color(0xffF1F1F1))
                            .align(Alignment.CenterVertically)
                    )
                    Box(
                        modifier.clickable { onWithdrawal() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "탈퇴하기",
                            style = fontStyle.copy(color = Red_300),
                        )
                    }
                }
            }
        }
    }
}
