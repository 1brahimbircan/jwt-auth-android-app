package com.example.jwtauth.repository
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jwtauth.api.UserAPI
import com.example.jwtauth.models.UserRequest
import com.example.jwtauth.models.UserResponse
import com.example.jwtauth.utils.NetworkResult
import com.example.jwtauth.utils.TokenManager
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(private val userAPI: UserAPI,private val tokenManager: TokenManager) {

    private val _userResponseLiveData = MutableLiveData<NetworkResult<UserResponse>>()
    val userResponseLiveData : LiveData<NetworkResult<UserResponse>> get() = _userResponseLiveData

    suspend fun loginUser(userRequest: UserRequest){
        _userResponseLiveData.postValue(NetworkResult.Loading())
        val response = userAPI.login(userRequest)
        handleResponse(response)
        Log.d("login:",response.body().toString())
    }
    suspend fun registerUser(userRequest: UserRequest) {
        _userResponseLiveData.postValue(NetworkResult.Loading())
        val response = userAPI.register(userRequest)
        handleResponse(response)
        Log.d("register:",response.body().toString())
    }

    suspend fun getUser() {
        _userResponseLiveData.postValue(NetworkResult.Loading())
        val token = "Bearer ${tokenManager.getToken()}"
        val response = userAPI.getUser(token)
        handleResponse(response)
        Log.d("getUser:",response.body().toString())
    }

    private fun handleResponse(response: Response<UserResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        }
        else if(response.errorBody() != null){
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _userResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        }
        else{
            _userResponseLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

}