package com.example.jwtauth.api

import com.example.jwtauth.models.User
import com.example.jwtauth.models.UserRequest
import com.example.jwtauth.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface UserAPI {
    @POST("login-user")
    suspend fun login(@Body userRequest: UserRequest): Response<UserResponse>

    @POST("register-user")
    suspend fun register(@Body userRequest: UserRequest) : Response<UserResponse>

    @GET("get-user")
    suspend fun getUser(
        @Header("Authorization") token: String
    ): Response<UserResponse>

}