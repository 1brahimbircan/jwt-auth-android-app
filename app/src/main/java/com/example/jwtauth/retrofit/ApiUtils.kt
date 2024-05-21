package com.example.jwtauth.retrofit

import com.example.jwtauth.utils.Constants.BASE_URL

class ApiUtils {
    companion object{
        fun getAuthDao() : AuthDao{
            return RetrofitClient.getClient(BASE_URL).create(AuthDao::class.java)
        }
    }
}
