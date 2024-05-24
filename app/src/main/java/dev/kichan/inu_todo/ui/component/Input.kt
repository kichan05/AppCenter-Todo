package dev.kichan.inu_todo.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.kichan.inu_todo.ui.theme.APP_ROUND
import dev.kichan.inu_todo.ui.theme.Gray_200
import dev.kichan.inu_todo.ui.theme.Gray_600
import dev.kichan.inu_todo.ui.theme.INUTodoTheme

@Composable
fun Input(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    icon: ImageVector? = null
) {
    BasicTextField(
        value = value,
        onValueChange = onChange,
        modifier
    ) { innerTextField ->
        Row(
            Modifier
                .fillMaxWidth()
                .background(color = Gray_200, shape = RoundedCornerShape(APP_ROUND))
                .padding(horizontal = 10.dp, vertical = 17.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = null, tint = Gray_600)
                Spacer(modifier = Modifier.width(8.dp))
            }

            if (value.isEmpty() && placeholder != null) {
                Text(
                    text = placeholder,
                    style = TextStyle(
                        color = Gray_600
                    )
                )
            }

            innerTextField()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InputPreview() {
    val input = remember { mutableStateOf("") }

    INUTodoTheme {
        Input(input.value, { input.value = it }, icon = Icons.Default.Search)
    }
}