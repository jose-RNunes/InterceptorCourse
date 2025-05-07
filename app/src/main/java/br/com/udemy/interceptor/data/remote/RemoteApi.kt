package br.com.udemy.interceptor.data.remote

import br.com.udemy.interceptor.data.remote.request.LoginRequest
import br.com.udemy.interceptor.data.remote.request.RefreshTokenRequest
import br.com.udemy.interceptor.data.remote.response.AuthResponse
import br.com.udemy.interceptor.data.remote.response.RefreshTokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RemoteApi {

    @POST("auth")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("auth/refresh-token")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): RefreshTokenResponse

}