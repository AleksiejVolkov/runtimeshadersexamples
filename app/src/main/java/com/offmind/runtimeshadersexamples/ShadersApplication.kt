package com.offmind.runtimeshadersexamples

import android.app.Application
import com.offmind.runtimeshadersexamples.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Custom Application class for initializing Koin dependency injection
 */
class ShadersApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Koin
        startKoin {
            // Use Android logger for debug builds
            androidLogger(Level.ERROR)
            // Provide Android context
            androidContext(this@ShadersApplication)
            // Load modules
            modules(appModule)
        }
    }
}