package com.example.jwtauth.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jwtauth.data.entities.ApiResponse
import com.example.jwtauth.data.entities.TokenResponse
import com.example.jwtauth.data.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepo:AuthRepository):ViewModel(){

    private val _loginResponse = MutableStateFlow<TokenResponse?>(null)
    val loginResponse: StateFlow<TokenResponse?> get() = _loginResponse

    private val _registerResponse = MutableStateFlow<ApiResponse?>(null)
    val registerResponse: StateFlow<ApiResponse?> get() = _registerResponse

    private val _updateResponse = MutableStateFlow<ApiResponse?>(null)
    val updateResponse: StateFlow<ApiResponse?> get() = _updateResponse

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = authRepo.login(email, password)
                _loginResponse.value = response
            } catch (e: Exception) {
                _loginResponse.value = TokenResponse(0, "Network Error: ${e.message}", "")
            }
        }
    }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = authRepo.register(name, email, password)
                _registerResponse.value = response
            } catch (e: Exception) {
                _registerResponse.value = ApiResponse(0, "Network Error: ${e.message}")
            }
        }
    }

    fun updateUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = authRepo.update(name, email, password)
                _updateResponse.value = response
            } catch (e: Exception) {
                _updateResponse.value = ApiResponse(0, "Network Error: ${e.message}")
            }
        }
    }
}


