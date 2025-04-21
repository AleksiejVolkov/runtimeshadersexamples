package com.offmind.runtimeshadersexamples.di

import com.offmind.runtimeshadersexamples.model.Project
import org.koin.dsl.module

/**
 * Koin module that provides application-level dependencies
 */
val appModule = module {
    // Provide Project as a singleton
    single { Project() }
}
