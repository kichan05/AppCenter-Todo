package dev.kichan.inu_todo.model.service

import dev.kichan.inu_todo.model.data.member.SignInReq
import dev.kichan.inu_todo.model.data.member.SignInRes
import dev.kichan.inu_todo.model.data.member.SignUpReq
import dev.kichan.inu_todo.model.data.member.SignUpRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MemberService {
    @POST("/member/sign-up")
    suspend fun signUp(@Body body: SignUpReq) : Response<SignUpRes>

    @POST("/member/sign-in")
    suspend fun signIn(@Body body: SignInReq) : Response<SignInRes>
}