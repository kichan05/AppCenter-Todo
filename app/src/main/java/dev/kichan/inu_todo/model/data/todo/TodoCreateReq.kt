package dev.kichan.inu_todo.model.data.todo

import dev.kichan.inu_todo.model.data.category.Category

data class TodoCreateReq(
    val category: Category,
    val content: String,
    val setDate: String,
)