package com.reportvox.reportvoxcontributor.di

import com.reportvox.reportvoxcontributor.AppViewModel
import com.reportvox.reportvoxcontributor.presentation.auth.LoginViewModel
import com.reportvox.reportvoxcontributor.presentation.home.ForYouViewModel
import com.reportvox.reportvoxcontributor.presentation.settings.SettingsViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

expect fun sharedPlatformModule(): Module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(commonModule, sharedPlatformModule())
}

// for ios
fun initKoin() {
    initKoin { }
}

val commonModule = module {
    factory { _ -> SettingsViewModel() }
    factory { _ -> ForYouViewModel() }
    factory { _ -> AppViewModel() }
    factory { _ -> LoginViewModel() }
}
