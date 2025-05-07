package br.com.udemy.interceptor.domain.usecase

import br.com.udemy.interceptor.domain.model.AuthModel
import br.com.udemy.interceptor.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginUseCase(private val loginRepository: LoginRepository) {

    operator fun invoke(userName: String, password: String): Flow<AuthModel> {
        return flow { emit(loginRepository.login(userName, password)) }
    }
}