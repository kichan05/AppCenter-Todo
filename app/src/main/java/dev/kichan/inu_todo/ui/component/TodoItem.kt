package dev.kichan.inu_todo.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.kichan.inu_todo.R
import dev.kichan.inu_todo.model.data.category.Category
import dev.kichan.inu_todo.model.data.todo.Todo
import dev.kichan.inu_todo.ui.CategoryColor
import dev.kichan.inu_todo.ui.theme.APP_ROUND
import dev.kichan.inu_todo.ui.theme.INUTodoTheme

@Composable
fun TodoItem(todo: Todo, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (todo.checked) {
            Surface(
                Modifier
                    .width(22.dp)
                    .height(22.dp),
                shape = RoundedCornerShape(100.dp),
                color = todo.category.colorValue,
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(5.dp)
                )
            }
        } else {
            Surface(
                Modifier
                    .width(22.dp)
                    .height(22.dp),
                shape = RoundedCornerShape(100.dp),
                border = BorderStroke(1.dp, todo.category.colorValue)
            ) { }
        }

        Spacer(modifier = Modifier.width(13.dp))

        val shape = RoundedCornerShape(12.dp)

        Text(
            text = todo.content,
            modifier = Modifier
                .weight(1f)
                .background(todo.category.colorValue.copy(alpha = 0.2f), shape)
                .border(1.dp, todo.category.colorValue, shape = shape)
                .padding(vertical = 11.dp, horizontal = 14.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TodoItemPreview() {
    INUTodoTheme {
        TodoItem(
            todo = Todo(
                todoId = 2226,
                category = Category(
                    categoryId = 2595,
                    content = "fermentum",
                    color = CategoryColor.GREEN.hex
                ),
                checked = true,
                content = "habitant",
                _setDate = "usu",
                _writeDate = "cetero"
            )
        )
    }
}