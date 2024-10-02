package com.app.orynarud_testtask_videolist

import android.app.Application
import com.app.orynarud_testtask_videolist.di.appModule
import com.app.orynarud_testtask_videolist.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@AppApplication)
            modules(appModule)
            modules(dataModule)
        }
    }
}
