package br.com.udemy.interceptor.data.local

interface InterceptorCache {

    fun saveToken(value: String)

    fun getToken(): String?

    fun saveRefreshToken(value: String)

    fun getRefreshToken(): String?
}

class InterceptorCacheImpl: InterceptorCache {

    private val cache = mutableMapOf<String, String>()

    override fun saveToken(value: String) {
        cache[ACCESS_TOKEN_KEY] = value
    }

    override fun getToken(): String? {
       return cache[ACCESS_TOKEN_KEY]
    }

    override fun saveRefreshToken(value: String) {
        cache[REFRESH_TOKEN_KEY] = value
    }

    override fun getRefreshToken(): String? {
        return cache[REFRESH_TOKEN_KEY]
    }

    private companion object {
        const val ACCESS_TOKEN_KEY = "access_token"
        const val REFRESH_TOKEN_KEY = "refresh_token"
    }
}