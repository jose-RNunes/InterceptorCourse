package br.com.udemy.interceptor.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.component.KoinComponent

object OkHttpConfig : KoinComponent {

    fun provideOKHttpClient() = defaultOkHttpClient().build()

    fun provideSignedOkHttpClient() = defaultOkHttpClient()
        .build()

    private fun defaultOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor())

    private fun loggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}