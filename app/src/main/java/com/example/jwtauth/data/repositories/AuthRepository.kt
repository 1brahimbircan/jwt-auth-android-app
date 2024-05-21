package com.example.jwtauth.data.repositories
import com.example.jwtauth.data.entities.ApiResponse
import com.example.jwtauth.data.entities.AuthRequest
import com.example.jwtauth.data.entities.TokenResponse
import com.example.jwtauth.data.entities.User
import com.example.jwtauth.retrofit.AuthDao
import com.example.jwtauth.utils.TokenManager

class AuthRepository(private val authDao: AuthDao,private val tokenManager: TokenManager) {
    suspend fun login(email:String, password: String) : TokenResponse {
        val authRequest = AuthRequest(email, password)
        val response = authDao.login(authRequest)

        if(response.status == 1){
            //Save the token in SharedPreferences
            tokenManager.saveToken(response.token)
        }
        return response
    }

    suspend fun register(name:String,email:String,password:String) : ApiResponse {
        val userData = User(name,email,password)
        return authDao.register(userData)
    }

    suspend fun update(name: String, email: String, password: String): ApiResponse {
        val userData = User(name,email, password)
        val token = tokenManager.getToken()
        return authDao.updateUser(token,userData)
    }

}