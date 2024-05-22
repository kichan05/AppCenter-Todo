package dev.kichan.inu_todo.model.data.todo

data class TodoCreateReq(
    val category: String,
    val content: String,
    val setDate: String,
    val writeDate: String
)