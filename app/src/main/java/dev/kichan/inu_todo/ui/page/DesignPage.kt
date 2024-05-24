package dev.kichan.inu_todo.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.kichan.inu_todo.ui.component.InuButton

@Preview(showBackground = true)
@Composable
fun DesignPage() {
    Column(Modifier.fillMaxSize()) {
        InuButton(
            onClick = { /*TODO*/ },
            Modifier.fillMaxWidth().padding(17.dp),
            text = "버튼",
        )
    }
}