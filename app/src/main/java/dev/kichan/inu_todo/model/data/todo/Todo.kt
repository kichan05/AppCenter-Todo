package dev.kichan.inu_todo.model.data.todo

data class Todo(
    val category: String,
    val checked: Boolean,
    val content: String,
    val setDate: String,
    val writeDate: String
)