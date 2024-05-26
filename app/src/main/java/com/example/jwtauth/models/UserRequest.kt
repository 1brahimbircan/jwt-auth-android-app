package com.example.jwtauth.models

data class UserRequest(
    val name:String,
    val email: String,
    val password: String
)