package com.example.jwtauth.data.repositories
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jwtauth.data.entities.ApiResponse
import com.example.jwtauth.data.entities.AuthRequest
import com.example.jwtauth.data.entities.AuthResult
import com.example.jwtauth.data.entities.TokenResponse
import com.example.jwtauth.data.entities.User
import com.example.jwtauth.retrofit.AuthDao
import com.example.jwtauth.data.entities.NetworkResult
import com.example.jwtauth.utils.TokenManager
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authDao: AuthDao,private val tokenManager: TokenManager) {

    private val _userResponseLiveData = MutableLiveData<NetworkResult<ApiResponse>>()
    val userResponseLiveData : LiveData<NetworkResult<ApiResponse>> get() = _userResponseLiveData

    private val _tokenResponseLiveData = MutableLiveData<AuthResult<TokenResponse>>()
    val tokenResponseLiveData : LiveData<AuthResult<TokenResponse>> get() = _tokenResponseLiveData


    suspend fun login(authRequest: AuthRequest){
        val response = authDao.login(authRequest)
        handleTokenResponse(response)
        Log.d("login:",response.body().toString())
        val token = tokenManager.getToken()
        Log.d("login:",token.toString())

    }

    suspend fun register(user: User) {
        val response = authDao.register(user)
        handleUserResponse(response)
        Log.d("register:",response.body().toString())
    }

    suspend fun update(user: User){
        val token = tokenManager.getToken()
        authDao.updateUser(token,user)
    }

    private fun handleTokenResponse(response: Response<TokenResponse>) {
        _tokenResponseLiveData.postValue(AuthResult.Loading())
        if (response.isSuccessful && response.body() != null && response.body()!!.status == 1) {
            _tokenResponseLiveData.postValue(AuthResult.Authorized(response.body()!!))
            tokenManager.saveToken(response.body()!!.token)
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _tokenResponseLiveData.postValue(AuthResult.Unauthorized(errorObj.getString("message")))
        } else{
            _tokenResponseLiveData.postValue(AuthResult.Unauthorized("Something went wrong"))
        }
    }
    private fun handleUserResponse(response: Response<ApiResponse>) {
        _userResponseLiveData.postValue(NetworkResult.Loading())
        if (response.isSuccessful && response.body() != null && response.body()!!.status == 1) {
            _userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _userResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _userResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

}