package dev.kichan.inu_todo.model.data.todo

import dev.kichan.inu_todo.model.data.category.Category

data class Todo(
    val todoId: Int,
    val category: Category,
    val checked: Boolean,
    val content: String,
    val setDate: String,
    val writeDate: String,
)