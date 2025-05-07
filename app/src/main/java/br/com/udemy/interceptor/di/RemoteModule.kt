package br.com.udemy.interceptor.di

import br.com.udemy.interceptor.BuildConfig
import br.com.udemy.interceptor.data.local.InterceptorCache
import br.com.udemy.interceptor.data.local.InterceptorCacheImpl
import br.com.udemy.interceptor.data.remote.interceptors.HeaderInterceptor
import br.com.udemy.interceptor.data.remote.interceptors.HeaderInterceptorImpl
import br.com.udemy.interceptor.data.remote.MoshiConfig
import br.com.udemy.interceptor.data.remote.OkHttpConfig
import br.com.udemy.interceptor.data.remote.RemoteApi
import br.com.udemy.interceptor.data.remote.RemoteSignedApi
import br.com.udemy.interceptor.data.remote.RetrofitConfig
import br.com.udemy.interceptor.data.remote.interceptors.Authenticate
import br.com.udemy.interceptor.data.remote.interceptors.AuthenticateImpl
import br.com.udemy.interceptor.data.remote.interceptors.ErrorInterceptor
import br.com.udemy.interceptor.data.remote.interceptors.ErrorInterceptorImpl
import br.com.udemy.interceptor.data.remote.interceptors.RefreshAuthorizationInterceptor
import br.com.udemy.interceptor.data.remote.interceptors.RefreshAuthorizationInterceptorImpl
import br.com.udemy.interceptor.data.remote.interceptors.UrlInterceptor
import br.com.udemy.interceptor.data.remote.interceptors.UrlInterceptorImpl
import br.com.udemy.interceptor.data.repository.CharacterRepositoryImpl
import br.com.udemy.interceptor.data.repository.LoginRepositoryImpl
import br.com.udemy.interceptor.domain.repository.CharacterRepository
import br.com.udemy.interceptor.domain.repository.LoginRepository
import br.com.udemy.interceptor.domain.usecase.GetCharacterUseCase
import br.com.udemy.interceptor.domain.usecase.GetCharactersUseCase
import br.com.udemy.interceptor.domain.usecase.LoginUseCase
import br.com.udemy.interceptor.presentation.chacaters.CharacterViewModel
import br.com.udemy.interceptor.presentation.character.CharacterDetailViewModel
import br.com.udemy.interceptor.presentation.login.LoginViewModel
import okhttp3.Authenticator
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import kotlin.math.sin

private const val OK_HTTP = "OK_HTTP"
private const val SIGNED_OK_HTTP = "SIGNED_OK_HTTP"

private const val RETROFIT = "RETROFIT"
private const val SIGNED_RETROFIT = "SIGNED_RETROFIT"

val remoteModule = module {
    single { MoshiConfig.build() }
    single<InterceptorCache> { InterceptorCacheImpl() }
    single(named(SIGNED_OK_HTTP)) { OkHttpConfig.provideSignedOkHttpClient() }
    single(named(OK_HTTP)) { OkHttpConfig.provideOKHttpClient() }
    single(named(RETROFIT)) {
        RetrofitConfig.provideRetrofit(
            client = get(named(OK_HTTP)),
            moshi = get(),
            url = getProperty(Properties.BASE_URL)
        )
    }
    single(named(SIGNED_RETROFIT)) {
        RetrofitConfig.provideRetrofit(
            client = get(named(SIGNED_OK_HTTP)),
            moshi = get(),
            url = getProperty(Properties.BASE_URL)
        )
    }
    single<RemoteSignedApi> { get<Retrofit>(named(SIGNED_RETROFIT)).create(RemoteSignedApi::class.java) }
    single<RemoteApi> { get<Retrofit>(named(RETROFIT)).create(RemoteApi::class.java) }
}

val dataModule = module {
    factory<CharacterRepository> {
        CharacterRepositoryImpl(remoteSignedApi = get())
    }
    factory<LoginRepository> { LoginRepositoryImpl(remoteApi = get(), interceptorCache = get()) }
}

val domainModule = module {
    factory { GetCharactersUseCase(characterRepository = get()) }
    factory { GetCharacterUseCase(characterRepository = get()) }
    factory { LoginUseCase(loginRepository = get()) }
}

val presentationModule = module {
    viewModelOf(::CharacterViewModel)
    viewModelOf(::CharacterDetailViewModel)
    viewModelOf(::LoginViewModel)
}