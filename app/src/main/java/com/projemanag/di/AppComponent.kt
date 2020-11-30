package com.projemanag.di

import com.projemanag.activities.BaseActivity
import com.projemanag.BaseApplication
import com.projemanag.activities.SignUpActivity
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
        ProductionModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance app: BaseApplication): AppComponent
    }

    fun inject(baseActivity: BaseActivity)
    fun inject(signUpActivity: SignUpActivity)
}