package br.com.udemy.interceptor.data.remote

import android.app.Application
import br.com.udemy.interceptor.di.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import org.koin.fileProperties

class InterceptorCourseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@InterceptorCourseApplication)
            androidLogger(Level.ERROR)
            fileProperties()
            module {
                remoteModule
            }
        }
    }
}