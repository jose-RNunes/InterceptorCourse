package br.com.udemy.interceptor

import android.app.Application
import br.com.udemy.interceptor.di.dataModule
import br.com.udemy.interceptor.di.domainModule
import br.com.udemy.interceptor.di.presentationModule
import br.com.udemy.interceptor.di.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.fileProperties

class InterceptorCourseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@InterceptorCourseApplication)
            fileProperties()
            modules(remoteModule, dataModule, domainModule, presentationModule)
        }
    }
}