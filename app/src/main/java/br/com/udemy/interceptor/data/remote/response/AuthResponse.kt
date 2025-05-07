package br.com.udemy.interceptor.data.remote.response

import br.com.udemy.interceptor.domain.model.AuthModel

data class AuthResponse(
    val user: String,
    val name: String,
    val accessToken: String,
    val refreshToken: String
) {

    fun toAuthModel() = AuthModel(
        user = user,
        name = name
    )
}
