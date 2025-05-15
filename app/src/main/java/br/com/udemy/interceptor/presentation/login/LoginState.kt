package br.com.udemy.interceptor.presentation.login

import br.com.udemy.interceptor.domain.model.AuthModel

data class LoginState(
    val showLoading: Boolean = false,
    val showErrorMessage: String? = null,
    val authModel: AuthModel? = null,
    val authSuccess: Boolean = false,
    val userNameInvalid: Boolean = false,
    val passwordInvalid: Boolean = false
) {
    fun showError() = showErrorMessage != null && showLoading.not()
}

sealed interface LoginAction {
    data class Login(val userName: String, val password: String): LoginAction
    data object OnNavigateSuccess: LoginAction
}
