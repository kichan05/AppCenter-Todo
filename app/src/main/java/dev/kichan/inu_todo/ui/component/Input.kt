package dev.kichan.inu_todo.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.kichan.inu_todo.R
import dev.kichan.inu_todo.ui.theme.APP_ROUND
import dev.kichan.inu_todo.ui.theme.Blue_100
import dev.kichan.inu_todo.ui.theme.Blue_400
import dev.kichan.inu_todo.ui.theme.Gray_200
import dev.kichan.inu_todo.ui.theme.Gray_600
import dev.kichan.inu_todo.ui.theme.INUTodoTheme
import dev.kichan.inu_todo.ui.theme.Red_100
import dev.kichan.inu_todo.ui.theme.Red_400

@Composable
fun Input(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    placeholder: String? = null,
    icon: ImageVector? = null,
    isSuccess: Boolean = false,
    isError: Boolean = false,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    inputType: InputType = InputType.Text,
) {
    val shape = RoundedCornerShape(APP_ROUND)
    val contentColor = if (isSuccess) Blue_400 else if (isError) Red_400 else Gray_600
    val backgroundColor = if (isSuccess) Blue_100 else if (isError) Red_100 else Gray_200
    val isContentShow = remember { mutableStateOf(false) }

    BasicTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        textStyle = textStyle.copy(color = contentColor),
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
    ) { innerTextField ->
        Row(
            modifier
                .background(color = backgroundColor, shape = shape)
                .let {
                    if (isSuccess || isError) it
                        .border(0.5.dp, contentColor, shape)
                    else it
                }
                .padding(horizontal = 10.dp, vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = null, tint = contentColor)
                Spacer(modifier = Modifier.width(8.dp))
            }

            Box(
                Modifier.fillMaxWidth()
            ) {
                if (value.isBlank() && placeholder != null) {
                    Text(
                        text = placeholder,
                        style = textStyle.copy(
                            color = contentColor
                        )
                    )
                }

                if(inputType == InputType.Text || isContentShow.value) {
                    innerTextField()
                }
                else {
                    Text(text = "*".repeat(value.length), color = contentColor)
                }

                if (inputType == InputType.Password) {
                    Image(
                        painter = painterResource(
                            id = if (isContentShow.value) R.drawable.ic_eye else R.drawable.ic_eye_closed
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .clickable {
                                isContentShow.value = !isContentShow.value
                            },
                        colorFilter = ColorFilter.tint(contentColor)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InputPreview() {
    val input = remember { mutableStateOf("") }

    INUTodoTheme {
        Input(
            input.value,
            { input.value = it },
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            placeholder = "이름을 입력하세요",
            icon = Icons.Default.Search,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InputSuccessPreview() {
    val input = remember { mutableStateOf("") }

    INUTodoTheme {
        Input(
            input.value,
            { input.value = it },
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            placeholder = "비밀번호를 입력하세요",
            icon = Icons.Default.Search,
            isSuccess = true,
            isError = true,
            inputType = InputType.Password
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InputErrorPreview() {
    val input = remember { mutableStateOf("") }

    INUTodoTheme {
        Input(
            input.value,
            { input.value = it },
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            isError = true,
            icon = Icons.Default.Search,
            placeholder = "이름을 입력하세요"
        )
    }
}

enum class InputType {
    Text, Password
}