package com.example.auth2.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth2.data.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {
    var loginResult: ((Boolean) -> Unit)? = null
    var registerResult: ((Boolean) -> Unit)? = null

    fun register(email: String, password: String, name: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val sucess = repository.regiterUser(email, password, name)
            onResult(sucess)
        }
    }

    fun login(email: String, password: String, onResult: (Boolean) -> Unit){
        viewModelScope.launch {
            val sucess = repository.loginUser(email, password)
            onResult(sucess)
        }
    }

    fun resetPassword(email: String, onResult: (Boolean) -> Unit){
        viewModelScope.launch {
            val sucess = repository.resetPassword(email)
            onResult(sucess)
        }
    }

    fun getUserName(onResult: (String?) -> Unit){
        viewModelScope.launch {
            val name = repository.getUserName()
            onResult(name)
        }
    }

    fun loginWithGoogle(idToken: String, onResult: (Boolean) -> Unit){
        viewModelScope.launch {
            val sucess = repository.loginWithGoogle(idToken)
            onResult(sucess)
        }
    }

    fun getGoogleSignInClient(context : Context): GoogleSignInClient {
        return repository.getGoogleSignClient(context)
    }

    fun logout(){
        repository.logout()
    }

}