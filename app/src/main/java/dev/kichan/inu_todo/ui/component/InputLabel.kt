package dev.kichan.inu_todo.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.inu_todo.ui.theme.INUTodoTheme
import dev.kichan.inu_todo.ui.theme.Red_400

@Composable
fun InputLabel(
    value: String,
    onChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    placeholder: String? = null,
    icon: ImageVector? = null,
    isSuccess: Boolean = false,
    error: String? = null,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
) {
    Column(Modifier.padding(17.dp)) {
        Row(
            Modifier.padding(start = 4.dp, bottom = 10.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = label,
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            )

            if (error != null) {
                Text(
                    text = error,
                    style = TextStyle(
                        color = Red_400,
                        fontSize = 14.sp,
                    ),
                    modifier = Modifier.padding(start = 9.dp)
                )
            }
        }
        Input(
            value = value,
            onChange = onChange,
            modifier = modifier,
            textStyle = textStyle,
            placeholder = placeholder,
            icon = icon,
            isSuccess = isSuccess,
            isError = error != null,
            singleLine = false,
            maxLines = maxLines,
            minLines = minLines,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InputLabelPreview() {
    INUTodoTheme {
        InputLabel(
            value = "",
            onChange = {},
            label = "이름",
            placeholder = "이름을 입력하세요.",
            icon = Icons.Default.Person
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InputLabelErrorPreview() {
    INUTodoTheme {
        InputLabel(
            value = "",
            onChange = {},
            label = "이름",
            placeholder = "이름을 입력하세요.",
            icon = Icons.Default.Person,
            error = "이미 사용중인 아이디입니다."
        )
    }
}