package com.projemanag

import android.app.Application
import com.projemanag.di.AppComponent
import com.projemanag.di.DaggerAppComponent
import com.projemanag.di.DaggerTestAppComponent
import com.projemanag.di.ProductionModule
import com.projemanag.di.TestAppComponent
import com.projemanag.di.TestModule
import com.projemanag.factroy.UserFactory
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject


@ExperimentalCoroutinesApi
@FlowPreview
class TestBaseApplication : Application() {

    lateinit var appComponent: TestAppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger(this)
    }

    private fun initDagger(app: TestBaseApplication): TestAppComponent =
        DaggerTestAppComponent.builder()
            .testModule(TestModule(app))
            .build()
}