package dev.kichan.inu_todo.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.inu_todo.ui.theme.INUTodoTheme

@Composable
fun InputLabel(
    value: String,
    onChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    placeholder: String? = null,
    icon: ImageVector? = null,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
) {
    Column(Modifier.padding(17.dp)) {
        Text(
            text = label,
            Modifier.padding(start = 4.dp, bottom = 10.dp),
            style = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        )
        Input(
            value = value,
            onChange = onChange,
            modifier = modifier,
            textStyle = textStyle,
            placeholder = placeholder,
            icon = icon,
            singleLine = false,
            maxLines = maxLines,
            minLines = minLines,
            isError = true,
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