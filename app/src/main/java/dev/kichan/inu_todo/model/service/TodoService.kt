package dev.kichan.inu_todo.model.service

import dev.kichan.inu_todo.model.data.ResponseMessage
import dev.kichan.inu_todo.model.data.category.Category
import dev.kichan.inu_todo.model.data.category.CreateCategoryReq
import dev.kichan.inu_todo.model.data.todo.Todo
import dev.kichan.inu_todo.model.data.todo.TodoCreateReq
import dev.kichan.inu_todo.model.data.todo.TodoCreateRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoService {
    @POST("/todo/{member_id}")
    suspend fun todoCreate(
        @Path("member_id")
        memberId: Int,
        @Body body : TodoCreateReq
    ) : Response<Todo>

    @GET("/todo/{member_id}")
    suspend fun getTodo(
        @Path("member_id")
        memberId: Int,
    ) : Response<List<Todo>>

    @PUT("/todo/{todoId}")
    suspend fun editTodo(
        @Path("todoId")
        todoId : Int,
        @Body
        body : Todo
    ) : Response<Todo>

    @DELETE("/todo/{todoId}")
    suspend fun deleteTodo(
        @Path("todoId")
        todoId : Int,
    ) : Response<ResponseMessage>
}