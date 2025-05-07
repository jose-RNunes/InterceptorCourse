package br.com.udemy.interceptor.domain.repository

import br.com.udemy.interceptor.domain.model.AuthModel

interface LoginRepository {

   suspend fun login(userName: String, password: String): AuthModel

   suspend fun refreshToken(): String?
}