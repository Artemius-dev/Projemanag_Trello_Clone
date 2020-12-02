package com.projemanag.di

import android.app.Application
import com.projemanag.BaseApplication
import com.projemanag.activities.BaseActivity
import com.projemanag.activities.SignInActivity
import com.projemanag.activities.SplashActivity
import com.projemanag.models.factory.UserFactory
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
//        AndroidSupportInjectionModule::class,
//        ActivityBuildersModule::class,
        ProductionModule::class
    ]
)
interface AppComponent {

//    @Component.Builder
//    interface Builder {
//        @BindsInstance
//        fun application(application: Application?): Builder?
//        fun build(): AppComponent?
//    }
//
//    fun getUserFactory(): UserFactory
//
//    fun inject(instance: DaggerApplication?)
//
//    override fun inject(app: BaseApplication)

    fun inject(target: BaseActivity)

    fun inject(target: SplashActivity)

    fun inject(target: SignInActivity)
}