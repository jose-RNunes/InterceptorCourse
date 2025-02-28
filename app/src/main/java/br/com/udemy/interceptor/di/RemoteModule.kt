package br.com.udemy.interceptor.di

import br.com.udemy.interceptor.data.remote.MoshiConfig
import br.com.udemy.interceptor.data.remote.OkHttpConfig
import br.com.udemy.interceptor.data.remote.RetrofitConfig
import org.koin.dsl.module

val remoteModule = module {
    single { MoshiConfig.build() }
    single { OkHttpConfig.provideOkHttpClient() }
    single {
        RetrofitConfig.provideRetrofit(
            client = get(),
            moshi = get(),
            url = ""
        )
    }
}