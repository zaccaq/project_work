package com.yourpackage.data.repository
import com.example.project_work.model.User
import com.yourpackage.data.dao.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(email: String, password: String, nome: String = "", cognome: String = ""): Long {
        return withContext(Dispatchers.IO) {
            val user = User(email = email, password = password, nome = nome, cognome = cognome)
            userDao.insertUser(user)
        }
    }

    suspend fun loginUser(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            userDao.checkUserCredentials(email, password)
        }
    }

    suspend fun getUserByEmail(email: String): User? {
        return withContext(Dispatchers.IO) {
            userDao.getUserByEmail(email)
        }
    }
}