package dev.kichan.inu_todo.ui.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kichan.inu_todo.model.data.category.CreateCategoryReq
import dev.kichan.inu_todo.ui.CategoryColor
import dev.kichan.inu_todo.ui.theme.suit
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun CategoryFrom(category: CreateCategoryReq?, onChange: (CreateCategoryReq) -> Unit) {
    val inputShape = RoundedCornerShape(12.dp)
    val colorList = CategoryColor.entries

    val selectCategoryIndex = remember {
        mutableStateOf(
            Math.max(
                0, colorList.map { it.hex }.indexOf(category!!.color)
            )
        )
    }
    val nameInput = remember { mutableStateOf(category?.content ?: "") }

    LaunchedEffect(nameInput.value) {
        snapshotFlow { nameInput.value }
            .distinctUntilChanged() // 중복된 값 무시
            .collect {
                Log.d("nameInput", it)
                onChange(
                    CreateCategoryReq(
                        content = nameInput.value,
                        color = colorList.get(selectCategoryIndex.value).hex
                    )
                )
            }
    }

    LaunchedEffect(selectCategoryIndex.value) {
        snapshotFlow { selectCategoryIndex.value }
            .distinctUntilChanged() // 중복된 값 무시
            .collect {
                Log.d("selectCategory", it.toString())
                onChange(
                    CreateCategoryReq(
                        content = nameInput.value,
                        color = colorList.get(selectCategoryIndex.value).hex
                    )
                )
            }
    }

    Column {
        BasicTextField(
            value = nameInput.value,
            onValueChange = { nameInput.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .shadow(3.dp, inputShape)
                .background(Color.White, inputShape)
                .border(1.dp, Color(0xffEAEAEA), inputShape)
        ) { innerTextField ->
            Row(
                Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    modifier = Modifier
                        .width(27.dp)
                        .height(27.dp)
                        .background(
                            colorList[selectCategoryIndex.value].color,
                            RoundedCornerShape(100.dp)
                        )
                )
                Spacer(modifier = Modifier.width(12.dp))
                Box {
                    if (nameInput.value.isEmpty()) {
                        Text(
                            text = "추가할 카테고리를 입력해주세요!",
                            style = TextStyle(
                                color = Color(0xffd0d0d0),
                                fontFamily = suit,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                        )
                    }
                    innerTextField()
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(top = 24.dp)
                .shadow(3.dp, inputShape)
                .background(Color.White, inputShape)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Color",
                style = TextStyle(
                    fontFamily = suit,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            )
            Row(
                Modifier
                    .padding(vertical = 27.dp, horizontal = 22.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                for (color in colorList) {
                    Surface(
                        modifier = Modifier
                            .width(55.dp)
                            .height(55.dp)
                            .clickable {
                                selectCategoryIndex.value = color.ordinal
                            },
                        color = color.color,
                        shape = RoundedCornerShape(100.dp)
                    ) {
                        if (selectCategoryIndex.value == color.ordinal) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}