package com.projemanag

import android.app.Application
import com.projemanag.di.TestModule
import dagger.BindsInstance
import dagger.hilt.DefineComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


//@ExperimentalCoroutinesApi
//@FlowPreview
@CustomTestApplication(BaseApplicationCore::class)
interface TestBaseApplication {}
//class TestBaseApplication : Application() {
//
//    lateinit var appComponent: TestAppComponent
//
//    override fun onCreate() {
//        super.onCreate()
//        appComponent = initDagger(this)
//    }
//
//    private fun initDagger(app: TestBaseApplication): TestAppComponent =
//        DaggerTestAppComponent.builder()
//            .testModule(TestModule(app))
//            .build()
//}
//
