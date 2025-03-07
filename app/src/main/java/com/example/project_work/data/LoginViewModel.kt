package com.yourpackage.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.yourpackage.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    init {
        val database = AppDatabase.getDatabase(application)
        repository = UserRepository(database.userDao())
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            try {
                if (email.isBlank() || password.isBlank()) {
                    _loginState.value = LoginState.Error("Email e password sono obbligatori")
                    return@launch
                }

                val isSuccess = repository.loginUser(email, password)

                if (isSuccess) {
                    _loginState.value = LoginState.Success
                } else {
                    _loginState.value = LoginState.Error("Email o password non validi")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Errore durante il login: ${e.message}")
            }
        }
    }

    // Per registrare un nuovo utente (da implementare in una schermata di registrazione)
    fun register(email: String, password: String, nome: String, cognome: String) {
        viewModelScope.launch {
            try {
                repository.registerUser(email, password, nome, cognome)
                // Gestire la risposta
            } catch (e: Exception) {
                // Gestire l'errore
            }
        }
    }

    // State per gestire lo stato del login
    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        object Success : LoginState()
        data class Error(val message: String) : LoginState()
    }
}