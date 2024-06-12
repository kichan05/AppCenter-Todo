package dev.kichan.inu_todo.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import dev.kichan.inu_todo.ui.theme.Blue_400
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
    @Composable
    fun WithdrawalDialog(
        onDismiss: () -> Unit,
        onWithdrawal: () -> Unit
    ) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(dismissOnClickOutside = true)
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
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        val modifier = Modifier
                            .weight(1.0f)
                            .padding(vertical = 22.dp)
                        val fontStyle = TextStyle(
                            fontWeight = FontWeight.Medium,
                            fontFamily = suit,
                            fontSize = 16.sp,
                        )

                        Text(
                            text = "취소",
                            modifier.clickable { onDismiss() },
                            style = fontStyle.copy(color = Gray_300),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "탈퇴하기",
                            modifier.clickable { onWithdrawal() },
                            style = fontStyle.copy(color = Red_300),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

    val isShowWithdrawalDialog = remember { mutableStateOf(false) }

    val withdrawal : () -> Unit = {
        val memberService = RetrofitBuilder.getService(MemberService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val res = memberService.delete(MainActivity.user.memberId)
            if(res.isSuccessful) {
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
            Column(
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .shadow(1.dp, RoundedCornerShape(12.dp))
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
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    val modifier = Modifier
                        .weight(1.0f)
                        .background(Blue_100)
                        .padding(vertical = 22.dp)
                    val fontStyle = TextStyle(
                        color = Blue_400,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                    )

                    Text(text = "로그아웃", modifier, style = fontStyle, textAlign = TextAlign.Center)
                    Text(
                        text = "비민번호 변경",
                        modifier,
                        style = fontStyle,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Surface(
                color = Blue_100,
                shape = RoundedCornerShape(12.dp),
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

    if(isShowWithdrawalDialog.value) {
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