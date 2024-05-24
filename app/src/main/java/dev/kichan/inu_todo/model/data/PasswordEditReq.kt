package dev.kichan.inu_todo.model.data

data class PasswordEditReq (
    val changePw: String,
    val confirmChangePw: String,
    val userPw: String
)