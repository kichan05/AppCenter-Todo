package dev.kichan.inu_todo.model.data.todo

import dev.kichan.inu_todo.model.data.category.Category
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Todo(
    val todoId: Int,
    val category: Category,
    val checked: Boolean,
    val content: String,
    @retrofit2.http.Field("setDate") val _setDate: String,
    @retrofit2.http.Field("writeDate") val _writeDate: String,
) {
    val getDate : LocalDate
        get() {
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val localDate = LocalDate.parse(this._setDate, dateFormatter)

            return localDate
        }

    val writeDate : LocalDate
        get() {
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val localDate = LocalDate.parse(this._writeDate, dateFormatter)

            return localDate
        }
}