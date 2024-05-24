package dev.kichan.inu_todo.model.data.member

data class PasswordEditReq (
    val changePw: String,
    val confirmChangePw: String,
    val userPw: String
)