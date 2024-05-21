package dev.kichan.inu_todo.model.data.member

data class SignUpReq(
    val userId: String,
    val userPw: String,
    val confirmUserPw: String,
)