package com.example.crudapp2


import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDAO: UserDAO) {

    suspend fun insertUser(user: User) = userDAO.insertUser()

    fun getAllUsers(): Flow<List<User>> = userDAO.getAllUsers()

    suspend fun updateUser(user: User) = userDAO.updateUser(user)

    suspend fun deleteUser(user: User) = userDAO.deleteUser(user)
}