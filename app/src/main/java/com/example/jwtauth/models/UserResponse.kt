package com.example.jwtauth.models

data class UserResponse(
    val token: String,
    val user: User
)