package com.example.jwtauth.ui.viewmodels

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jwtauth.data.entities.ApiResponse
import com.example.jwtauth.data.entities.AuthRequest
import com.example.jwtauth.data.entities.AuthResult
import com.example.jwtauth.data.repositories.AuthRepository
import com.example.jwtauth.data.entities.NetworkResult
import com.example.jwtauth.data.entities.TokenResponse
import com.example.jwtauth.data.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepo:AuthRepository):ViewModel(){

    val userResponseLiveData : LiveData<NetworkResult<ApiResponse>> get() = authRepo.userResponseLiveData
    val tokenResponseLiveData: LiveData<AuthResult<TokenResponse>> get() = authRepo.tokenResponseLiveData


    fun login(authRequest: AuthRequest) {
        viewModelScope.launch {
            authRepo.login(authRequest)
        }
    }

    fun register(user: User) {
        viewModelScope.launch {
            authRepo.register(user)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            authRepo.update(user)
        }
    }

    fun validateCredentials(fullName: String? = null, emailAddress: String, password: String, confirmPassword: String? = null, isLogin: Boolean): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (!isLogin) {
            if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(emailAddress) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                return Pair(false, "Please provide all the required credentials")
            }
            if (password != confirmPassword) {
                return Pair(false, "Password didn't match, please try again")
            }
        } else {
            if (TextUtils.isEmpty(emailAddress) || TextUtils.isEmpty(password)) {
                return Pair(false, "Please provide email and password")
            }
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            return Pair(false, "Please provide a valid email")
        }
        if (password.length <= 5) {
            return Pair(false, "Password length should be greater than 5")
        }
        return result
    }



}


