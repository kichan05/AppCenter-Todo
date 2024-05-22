package dev.kichan.inu_todo.model.service

import dev.kichan.inu_todo.model.data.todo.Todo
import dev.kichan.inu_todo.model.data.todo.TodoCreateReq
import dev.kichan.inu_todo.model.data.todo.TodoCreateRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TodoService {
    @POST("/todo/{member_id}")
    suspend fun todoCreate(
        @Path("member_id")
        memberId: Int,
        @Body body : TodoCreateReq
    ) : Response<TodoCreateRes>

    @GET("/todo/{member_id}")
    suspend fun getTodo(
        @Path("member_id")
        memberId: String,
    ) : List<Todo>

    //Todo: Todo 수정
    //Todo: Todo 삭제
}