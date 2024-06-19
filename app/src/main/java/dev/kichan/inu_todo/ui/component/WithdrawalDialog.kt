package dev.kichan.inu_todo.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import dev.kichan.inu_todo.R
import dev.kichan.inu_todo.ui.theme.Gray_300
import dev.kichan.inu_todo.ui.theme.INUTodoTheme
import dev.kichan.inu_todo.ui.theme.Red_300
import dev.kichan.inu_todo.ui.theme.suit

@Composable
fun WithdrawalDialog(
    onDismiss: () -> Unit,
    onWithdrawal: () -> Unit,
    withdrawalText: String,
    @DrawableRes image: Int,
    content: @Composable ColumnScope.() -> Unit
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
                Column(content = content)
                Spacer(modifier = Modifier.height(15.dp))
                Image(
                    painter = painterResource(id = image),
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
                            text = withdrawalText,
                            style = fontStyle.copy(color = Red_300),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DialogPreview() {
    INUTodoTheme {
        WithdrawalDialog(
            onDismiss = {},
            onWithdrawal = {},
            withdrawalText = "탈퇴하기",
            image = R.drawable.boom
        ) {
            Text(
                text = "김사랑님,\n정말 계정을 삭제하실건가요?",
                fontFamily = suit,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}
