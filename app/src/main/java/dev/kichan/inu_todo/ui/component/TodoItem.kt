package dev.kichan.inu_todo.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.kichan.inu_todo.model.data.todo.Todo
import dev.kichan.inu_todo.ui.theme.INUTodoTheme

@Composable
fun TodoItem(todo: Todo) {
    Text(text = todo.content,
        modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp, horizontal = 8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun TodoItemPreview() {
    INUTodoTheme {
        TodoItem(
            todo = Todo(
                todoId = 2226,
                category = "lacinia",
                checked = false,
                content = "habitant",
                setDate = "usu",
                writeDate = "cetero"
            )
        )
    }
}