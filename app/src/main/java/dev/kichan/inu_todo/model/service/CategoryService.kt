package dev.kichan.inu_todo.model.service

import dev.kichan.inu_todo.model.data.ResponseMessage
import dev.kichan.inu_todo.model.data.category.Category
import dev.kichan.inu_todo.model.data.category.CreateCategoryReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CategoryService {
    @GET("/category/categories")
    suspend fun getUserCategory(
        @Header("Authorization") authorization : String,
    ) : Response<List<Category>>

    @POST("/category")
    suspend fun createCategory(
        @Header("Authorization") authorization : String,
        @Body
        body : CreateCategoryReq
    ) : Response<Category>

    @PUT("/category/{categoryId}")
    suspend fun editCategory(
        @Header("Authorization") authorization : String,
        @Path("categoryId")
        categoryId : Int,
        @Body
        body : CreateCategoryReq
    ) : Response<Category>

    @DELETE("/category/{categoryId}")
    suspend fun deleteCategory(
        @Header("Authorization") authorization : String,
        @Path("categoryId")
        categoryId : Int,
    ) : Response<ResponseMessage>
}