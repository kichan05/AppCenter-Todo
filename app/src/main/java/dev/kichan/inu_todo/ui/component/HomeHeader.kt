package dev.kichan.inu_todo.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.kichan.inu_todo.R

@Preview(showBackground = true)
@Composable
fun HomeHeader(onGoMyPage : () -> Unit = {}) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 22.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.todo),
            contentDescription = null,
            Modifier.width(100.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_profile_circle),
            contentDescription = null,
            modifier = Modifier.width(28.dp).clickable { onGoMyPage() },
        )
    }
}