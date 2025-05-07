package br.com.udemy.interceptor.data.repository

import br.com.udemy.interceptor.data.local.InterceptorCache
import br.com.udemy.interceptor.data.remote.RemoteApi
import br.com.udemy.interceptor.data.remote.request.LoginRequest
import br.com.udemy.interceptor.data.remote.request.RefreshTokenRequest
import br.com.udemy.interceptor.domain.model.AuthModel
import br.com.udemy.interceptor.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val remoteApi: RemoteApi,
    private val interceptorCache: InterceptorCache
) : LoginRepository {

    override suspend fun login(userName: String, password: String): AuthModel {
        val request = LoginRequest(userName, password)

        val data = remoteApi.login(request)

        interceptorCache.saveToken(data.accessToken)
        interceptorCache.saveRefreshToken(data.refreshToken)

        return data.toAuthModel()
    }

    override suspend fun refreshToken(): String {
        val refreshToken = interceptorCache.getRefreshToken().orEmpty()

        val data = remoteApi.refreshToken(RefreshTokenRequest(refreshToken))

        interceptorCache.saveToken(data.accessToken)
        interceptorCache.saveRefreshToken(data.refreshToken)
        return data.accessToken
    }
}