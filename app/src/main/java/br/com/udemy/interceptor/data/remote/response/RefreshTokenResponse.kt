package br.com.udemy.interceptor.data.remote.response

data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String
)
