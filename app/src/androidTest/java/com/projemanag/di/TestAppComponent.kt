package com.projemanag.di

import android.app.Application
import com.projemanag.LogInTest
import com.projemanag.TestBaseApplication
import com.projemanag.activities.SplashActivity
import com.projemanag.factroy.UserFactory
import com.projemanag.splash.SplashActivityTest
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@FlowPreview
@Singleton
@Component(
    modules = [
        TestModule::class
//        ActivityBuildersModule::class,
//        AndroidSupportInjectionModule::class
    ]
)
interface TestAppComponent {

//    @Component.Builder
//    interface Builder {
//        @BindsInstance
//        fun application(application: Application?): Builder?
//        fun build(): TestAppComponent?
//    }
//
//    fun inject(instance: DaggerApplication?)
//
//    override fun inject(app: TestBaseApplication)
//
//    fun getUserFactory(): UserFactory

    fun inject(target: SplashActivityTest)
}