package com.example.crudapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.crudapp2.AppDatabase
import com.example.crudapp2.UserRepository
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {
    private val repository: UserRepository

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    init {
        val userDAO = AppDatabase.getDatabase(application).userDAO()
        repository = UserRepository(userDAO)
        observeUsers()
    }

    private fun observeUsers(){
        viewModelScope.launch {
            repository.getAllUsers().collectLatest { userList ->
                _users.value = userList
            }
        }
    }

    fun insertUser(user: User){
        viewModelScope.launch { repository.insertUser(user)}
    }

    fun updateUser(user: User){
        viewModelScope.launch { repository.updateUser(user)}
    }

    fun deleteUser(user: User){
        viewModelScope.launch { repository.deleteUser(user)}
    }


}