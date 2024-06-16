package dev.kichan.inu_todo.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import dev.kichan.inu_todo.model.data.category.Category
import dev.kichan.inu_todo.model.data.todo.Todo
import dev.kichan.inu_todo.ui.CategoryColor
import dev.kichan.inu_todo.ui.theme.INUTodoTheme
import kotlin.math.roundToInt

@Composable
fun TodoItem(todo: Todo, modifier: Modifier = Modifier, onClick: (Todo) -> Unit) {
    val swipeOffsetX = remember { mutableStateOf(0.0f) }

    @Composable
    fun TodoItemContent() {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .offset {
                    IntOffset(swipeOffsetX.value.roundToInt(), 0)
                }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { change, dragAmount ->
                        change.consume()
                        swipeOffsetX.value = (swipeOffsetX.value + dragAmount).coerceIn(-300f, 0f)
                    }
                }
                .zIndex(1f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val checkModifier = Modifier
                .width(22.dp)
                .height(22.dp)
                .clickable { onClick(todo) }

            if (todo.checked) {
                Surface(
                    checkModifier,
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
                    checkModifier,
                    shape = RoundedCornerShape(100.dp),
                    border = BorderStroke(1.dp, todo.category.colorValue)
                ) { }
            }

            Spacer(modifier = Modifier.width(13.dp))

            val shape = RoundedCornerShape(12.dp)

            Text(
                text = todo.content,
                modifier = Modifier
                    .weight(1.0f)
                    .background(todo.category.colorValue.copy(alpha = 0.2f), shape)
                    .border(1.dp, todo.category.colorValue, shape = shape)
                    .padding(vertical = 11.dp, horizontal = 14.dp)
            )
        }
    }

    Box {
        if (swipeOffsetX.value < -250) {
            Row(
                modifier = Modifier
                    .zIndex(0f)
                    .align(Alignment.CenterEnd)
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        Modifier
                            .width(20.dp)
                            .height(20.dp)
                    )
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        Modifier
                            .width(20.dp)
                            .height(20.dp)
                    )
                }
            }
        }

        TodoItemContent()
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
//                _writeDate = "cetero"
            ),
            onClick = {}
        )
    }
}