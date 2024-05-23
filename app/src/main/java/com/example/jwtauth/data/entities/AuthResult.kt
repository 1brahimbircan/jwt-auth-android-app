package com.example.jwtauth.data.entities

sealed class AuthResult<T>(val data: T? = null,val message:String? = null) {
    class Authorized<T>(data : T): AuthResult<T>(data)
    class Unauthorized<T>(message:String?,data:T? = null): AuthResult<T>(data,message)
    class Loading<T>: AuthResult<T>()
}

