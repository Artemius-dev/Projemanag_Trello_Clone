package com.projemanag

import android.app.Application
import com.projemanag.di.DaggerTestAppComponent
import com.projemanag.di.TestAppComponent
import com.projemanag.di.TestModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


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