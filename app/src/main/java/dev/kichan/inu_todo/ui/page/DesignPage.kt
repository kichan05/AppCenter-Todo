package dev.kichan.inu_todo.ui.page

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.kichan.inu_todo.ui.component.Input
import dev.kichan.inu_todo.ui.component.InuButton
import dev.kichan.inu_todo.ui.theme.Blue_400

@Preview(showBackground = true)
@Composable
fun DesignPage() {
    Column(Modifier.fillMaxSize()) {
        InuButton(
            onClick = { /*TODO*/ },
            text = "버튼",
            Modifier
                .fillMaxWidth()
                .padding(17.dp),
            backgroundColor = Color.White,
            textColor = Blue_400,
            border = BorderStroke(1.dp, Blue_400),
        )

        Input(
            value = "",
            onChange = {},
            Modifier
                .fillMaxWidth ()
                .padding(17.dp),
            placeholder = "이름을 입력하세요.",
            icon = Icons.Default.Notifications,
            isSuccess = true,
            isError = true
        )
    }
}