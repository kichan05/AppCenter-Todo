package dev.kichan.inu_todo.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import dev.kichan.inu_todo.ui.theme.Blue_200
import dev.kichan.inu_todo.ui.theme.Blue_400
import dev.kichan.inu_todo.ui.theme.INUTodoTheme
import dev.kichan.inu_todo.ui.theme.suit
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle

@Composable
fun DatePicker(selectDay: LocalDate, onSelect: (LocalDate) -> Unit, onDismiss : () -> Unit) {
    val currentMonth = remember { mutableStateOf(YearMonth.now()) }

    val state = rememberCalendarState(
        firstVisibleMonth = currentMonth.value,
        startMonth = currentMonth.value
    )

    val onMonthPrev = {
        currentMonth.value = currentMonth.value.previousMonth
    }

    val onMonthNext = {
        currentMonth.value = currentMonth.value.nextMonth
    }

    @Composable
    fun Day(day: CalendarDay, isSelect: Boolean, isTodo : Boolean) {
        Column(
            Modifier
                .aspectRatio(0.9f)
                .clickable { onSelect(day.date) },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .width(32.dp)
                    .height(32.dp)
                    .background(if (isSelect) Blue_200 else Color.Transparent, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day.date.dayOfMonth.toString(),
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            if (isTodo) {
                Spacer(
                    modifier = Modifier
                        .width(6.dp)
                        .height(6.dp)
                        .background(Blue_400, CircleShape)
                )
            }
        }
    }

    @Composable
    fun daysWeek() {
        Row {
            for (day in daysOfWeek()) {
                Text(
                    text = day.getDisplayName(
                        TextStyle.SHORT,
                        java.util.Locale.getDefault()
                    ),
                    modifier = Modifier.weight(1.0f),
                    textAlign = TextAlign.Center,
                    style = androidx.compose.ui.text.TextStyle(
                        color = Color(0xff8e8e8e),
                        fontSize = 11.sp,
                        fontFamily = suit,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }

    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(12.dp))
        ) {
            Column(
                Modifier.padding(vertical = 25.dp, horizontal = 19.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${currentMonth.value.year}년 ${currentMonth.value.monthValue}월",
                        Modifier.padding(start = 9.dp),
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = suit
                        )
                    )
                    Row {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = null,
                            tint = Blue_400,
                            modifier = Modifier.clickable { onMonthPrev() }
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = null,
                            tint = Blue_400,
                            modifier = Modifier.clickable { onMonthNext() }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                HorizontalCalendar(
                    state = state,
                    dayContent = {
                        if (it.position == DayPosition.MonthDate) Day(
                            day = it,
                            isSelect = selectDay == it.date,
                            isTodo = false
                        )
                    },
                    monthHeader = { daysWeek() }
                )
                Spacer(modifier = Modifier.height(20.dp))
                InuButton(onClick = { onDismiss() }, text = "확인", Modifier.fillMaxWidth())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DatePickerPreview() {
    val selectDate = remember { mutableStateOf(LocalDate.now()) }
    INUTodoTheme {
        DatePicker(selectDate.value, { selectDate.value = it }, {})
    }
}