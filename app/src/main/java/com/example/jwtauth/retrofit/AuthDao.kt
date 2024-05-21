package com.example.jwtauth.retrofit

import com.example.jwtauth.data.entities.ApiResponse
import com.example.jwtauth.data.entities.AuthRequest
import com.example.jwtauth.data.entities.TokenResponse
import com.example.jwtauth.data.entities.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthDao {
    @POST("/login-user")
    suspend fun login( @Body request: AuthRequest): TokenResponse

    @POST("/register-user")
    suspend fun register( @Body request: User): ApiResponse

    @PATCH("/update-user")
    suspend fun updateUser(
        @Header("Authorization") token: String?,
        @Body data: User
    ) : ApiResponse

    @DELETE("/delete-user")
    suspend fun deleteUser(
        @Header("Authorization") token: String,
    )
}