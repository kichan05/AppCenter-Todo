package dev.kichan.inu_todo.model.service

import dev.kichan.inu_todo.model.data.category.Category
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CategoryService {
    @GET("/category/{memberId}")
    suspend fun getUserCategory(
        @Path("memberId")
        memberId: String
    ) : List<Category>

    @POST("/category/{memberId}")
    suspend fun createCategory(
        @Path("memberId")
        memberId: String
    ) : Category

    //todo: 카테고리 수정
    //todo: 카테고리 삭제
}