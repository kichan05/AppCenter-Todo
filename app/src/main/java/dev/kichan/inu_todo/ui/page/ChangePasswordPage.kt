package dev.kichan.inu_todo.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.kichan.inu_todo.MainActivity
import dev.kichan.inu_todo.model.RetrofitBuilder
import dev.kichan.inu_todo.model.data.member.PasswordEditReq
import dev.kichan.inu_todo.model.service.MemberService
import dev.kichan.inu_todo.ui.component.Header
import dev.kichan.inu_todo.ui.component.InputLabel
import dev.kichan.inu_todo.ui.component.InputType
import dev.kichan.inu_todo.ui.component.InuButton
import dev.kichan.inu_todo.ui.theme.INUTodoTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.notifyAll

@Composable
fun ChangePasswordPage(navController: NavHostController) {
    val password = remember { mutableStateOf("") }
    val newPassword = remember { mutableStateOf("") }
    val newPasswordConfirm = remember { mutableStateOf("") }

    val onChangePassword : () -> Unit = {
        val memberService = RetrofitBuilder.getService(MemberService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val res = memberService.editPassword(
                authorization = MainActivity.token,
                body = PasswordEditReq(
                    changePw = newPassword.value,
                    confirmChangePw = newPasswordConfirm.value,
                    userPw = password.value
                )
            )

            if(res.isSuccessful) {
                withContext(Dispatchers.Main) {
                    navController.popBackStack()
                }
            }
        }
    }

    Column(Modifier.fillMaxSize()) {
        Header(title = "비밀번호 변경") { navController.popBackStack() }

        Column(
            Modifier
                .padding(vertical = 8.dp, horizontal = 17.dp)
                .weight(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(19.dp)
            ) {
                InputLabel(
                    value = password.value,
                    onChange = { password.value = it },
                    label = "비밀번호",
                    placeholder = "비밀번호를 입력해주세요.",
                    inputType = InputType.Password,
                    icon = Icons.Outlined.Lock,
                    isSuccess = password.value.length > 8,
                )
                InputLabel(
                    value = newPassword.value,
                    onChange = { newPassword.value = it },
                    label = "변경 비밀번호",
                    placeholder = "비밀번호를 입력해주세요.",
                    inputType = InputType.Password,
                    icon = Icons.Outlined.Lock,
                    isSuccess = newPassword.value.length > 8 && newPassword.value == newPasswordConfirm.value,
                )
                InputLabel(
                    value = newPasswordConfirm.value,
                    onChange = { newPasswordConfirm.value = it },
                    label = "변경 비밀번호 확인",
                    placeholder = "비밀번호를 입력해주세요.",
                    inputType = InputType.Password,
                    icon = Icons.Outlined.Lock,
                    isSuccess = newPasswordConfirm.value.length > 8 && newPasswordConfirm.value == newPassword.value,
                )
            }

            InuButton(
                onClick = { onChangePassword() },
                text = "변경하기",
                Modifier.fillMaxWidth(),
                isDisable = password.value.isEmpty() || newPassword.value.isEmpty() || newPasswordConfirm.value.isEmpty()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChangePasswordPage() {
    INUTodoTheme {
        ChangePasswordPage(navController = rememberNavController())
    }
}