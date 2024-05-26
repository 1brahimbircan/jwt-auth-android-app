package com.example.jwtauth.ui.viewmodels

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jwtauth.repository.AuthRepository
import com.example.jwtauth.models.User
import com.example.jwtauth.models.UserRequest
import com.example.jwtauth.models.UserResponse
import com.example.jwtauth.utils.Helper
import com.example.jwtauth.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository):ViewModel(){

    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = authRepository.userResponseLiveData

    fun registerUser(userRequest: UserRequest) {
        viewModelScope.launch {
            authRepository.registerUser(userRequest)
        }
    }
    fun loginUser(userRequest: UserRequest) {
        viewModelScope.launch {
            authRepository.loginUser(userRequest)
        }
    }

    fun getUser(){
        viewModelScope.launch {
            authRepository.getUser()
        }
    }

    fun validateCredentials(emailAddress: String, userName: String, password: String, confirmPassword: String,
                            isLogin: Boolean) : Pair<Boolean, String> {

        var result = Pair(true, "")
        if(TextUtils.isEmpty(emailAddress) || (!isLogin && TextUtils.isEmpty(userName)) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)){
            result = Pair(false, "Please provide the credentials")
        }
        else if(!Helper.isValidEmail(emailAddress)){
            result = Pair(false, "Email is invalid")
        }
        else if(!TextUtils.isEmpty(password) && password.length <= 5){
            result = Pair(false, "Password length should be greater than 5")
        }
        else if(password != confirmPassword){
            result = Pair(false, "Password doesn't match")
        }
        return result
    }

}


