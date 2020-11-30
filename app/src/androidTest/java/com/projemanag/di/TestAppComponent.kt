package com.projemanag.di

import com.projemanag.LogInTest
import com.projemanag.TestBaseApplication
import com.projemanag.splash.SplashActivityTest
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@FlowPreview
@Singleton
@Component(
    modules = [
        AppModule::class,
        TestModule::class
    ]
)
interface TestAppComponent : AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app : TestBaseApplication): TestAppComponent
    }

    fun inject(splashActivityTest: SplashActivityTest)

    fun inject(logInTest: LogInTest)
}