package dev.kichan.inu_todo.ui.page

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.kichan.inu_todo.MainActivity
import dev.kichan.inu_todo.model.RetrofitBuilder
import dev.kichan.inu_todo.model.data.member.SignUpReq
import dev.kichan.inu_todo.model.service.MemberService
import dev.kichan.inu_todo.ui.component.Header
import dev.kichan.inu_todo.ui.component.InputLabel
import dev.kichan.inu_todo.ui.component.InputType
import dev.kichan.inu_todo.ui.component.InuButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Preview(showBackground = true)
@Composable
fun SignUpPage(navController: NavController = rememberNavController()) {
    val id = remember { mutableStateOf("") }
    val pass = remember { mutableStateOf("") }
    val passConfirm = remember { mutableStateOf("") }

    val signUp: (String, String, Context) -> Unit = { id, pass, context ->
        CoroutineScope(Dispatchers.IO).launch {
            val service = RetrofitBuilder.getService(MemberService::class.java)

            val result = service.signUp(
                body = SignUpReq(
                    userId = id,
                    userPw = pass,
                    confirmUserPw = pass
                )
            )

            if (result.isSuccessful) {
//                MainActivity.user = result.body()!!
                withContext(Dispatchers.Main) {
                    navController.popBackStack()
//                    navController.navigate(Page.Home.name)
//                    navController.clearBackStack(Page.SIGN_UP.name)
//                    navController.clearBackStack(Page.MAIN.name)
                }
            } else {
                MainActivity.showToast(context, "회원가입 실패")
            }
        }
    }

    Column(Modifier.fillMaxSize()) {
        Header(title = "회원가입") { navController.popBackStack() }
        Column(
            Modifier
                .weight(1f)
                .padding(vertical = 8.dp, horizontal = 17.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(19.dp)
            ) {
                InputLabel(
                    value = id.value,
                    onChange = { id.value = it },
                    label = "아이디",
                    placeholder = "아이디를 입력해주세요",
                    modifier = Modifier.fillMaxWidth(),
                    icon = Icons.Outlined.Person
                )

                InputLabel(
                    value = pass.value,
                    onChange = { pass.value = it },
                    label = "비밀번호",
                    placeholder = "비밀번호를 입력해주세요",
                    modifier = Modifier.fillMaxWidth(),
                    icon = Icons.Outlined.Lock,
                    inputType = InputType.Password,
                )

                InputLabel(
                    value = passConfirm.value,
                    onChange = { passConfirm.value = it },
                    label = "비밀번호 확인",
                    placeholder = "비밀번호를 입력해주세요",
                    modifier = Modifier.fillMaxWidth(),
                    icon = Icons.Outlined.Lock,
                    inputType = InputType.Password,
                )
            }

            val context = LocalContext.current

            InuButton(
                onClick = { signUp(id.value, pass.value, context) },
                text = "로그인",
                modifier = Modifier.fillMaxWidth(),
                isDisable = id.value.isBlank() || pass.value.isBlank() || passConfirm.value.isBlank()
            )
        }
    }
}