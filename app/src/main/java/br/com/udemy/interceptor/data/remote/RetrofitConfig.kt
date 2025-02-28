package br.com.udemy.interceptor.data.remote

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitConfig {

    fun provideRetrofit(
        client: OkHttpClient,
        moshi: Moshi,
        url: String
    ): Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}