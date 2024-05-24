package dev.kichan.inu_todo.model.service

import dev.kichan.inu_todo.model.data.category.Category
import dev.kichan.inu_todo.model.data.category.CreateCategoryReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CategoryService {
    @GET("/category/{memberId}")
    suspend fun getUserCategory(
        @Path("memberId")
        memberId: Int
    ) : Response<List<Category>>

    @POST("/category/{memberId}")
    suspend fun createCategory(
        @Path("memberId")
        memberId: Int,
        @Body
        body : CreateCategoryReq
    ) : Response<Category>
}