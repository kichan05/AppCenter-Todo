package dev.kichan.inu_todo.model.service

import dev.kichan.inu_todo.model.data.member.PasswordEditReq
import dev.kichan.inu_todo.model.data.ResponseMessage
import dev.kichan.inu_todo.model.data.member.SignInReq
import dev.kichan.inu_todo.model.data.member.SignInRes
import dev.kichan.inu_todo.model.data.member.SignUpReq
import dev.kichan.inu_todo.model.data.member.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MemberService {
    @POST("/member/sign-up")
    suspend fun signUp(@Body body: SignUpReq) : Response<User>

    @POST("/member/sign-in")
    suspend fun signIn(@Body body: SignInReq) : Response<SignInRes>

    @DELETE("/member")
    suspend fun delete(
        @Header("Authorization") authorization : String,
    ) : Response<ResponseMessage>

    @PUT("/member")
    suspend fun editPassword(
        @Header("Authorization") authorization : String,
        @Body
        body : PasswordEditReq
    ) : Response<User>
}