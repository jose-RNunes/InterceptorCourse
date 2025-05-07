package br.com.udemy.interceptor.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.udemy.interceptor.data.remote.response.ErrorResponse
import br.com.udemy.interceptor.domain.usecase.LoginUseCase
import br.com.udemy.interceptor.presentation.chacaters.CharacterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun dispatchAction(action: LoginAction) {
        when(action) {
            is LoginAction.Login -> {
                login(action.userName, action.password)
            }
            LoginAction.OnNavigateSuccess -> _state.update { it.copy(authSuccess = false) }
        }
    }

    private fun login(userName: String, password: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(showLoading = true)
            }
            loginUseCase(userName, password)
                .catch { error ->
                    val message = if (error is ErrorResponse) {
                        error.errorMessage
                    } else {
                        error.message
                    }
                    _state.update {
                        it.copy(showLoading = false, showErrorMessage = message)
                    }
                }.collect {
                    _state.update {
                        it.copy(showLoading = false, authSuccess = true)
                    }
                }
        }
    }
}