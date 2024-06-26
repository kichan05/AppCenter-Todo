package dev.kichan.inu_todo.model.data.todo

import com.google.gson.annotations.SerializedName
import dev.kichan.inu_todo.model.data.category.Category
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Todo(
    val todoId: Int,
    val category: Category,
    val checked: Boolean,
    val content: String,
    @SerializedName("setDate") val _setDate: String,
//    @SerializedName("writeDate") val _writeDate: String,
) {
    val setDate : LocalDate
        get() {
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val localDate = LocalDate.parse(this._setDate, dateFormatter)

            return localDate
        }

//    val writeDate : LocalDate
//        get() {
//            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//            val localDate = LocalDate.parse(this._writeDate, dateFormatter)
//
//            return localDate
//        }
}