package com.example.jwtauth.data.entities

data class TokenResponse(
    val status:Int,
    val message:String,
    val token:String
)