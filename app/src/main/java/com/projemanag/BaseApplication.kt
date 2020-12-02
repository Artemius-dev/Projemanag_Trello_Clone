package com.projemanag

import android.app.Application
import com.projemanag.di.AppComponent
import com.projemanag.di.DaggerAppComponent
import com.projemanag.di.ProductionModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
open class BaseApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger(this)
    }

    private fun initDagger(app: BaseApplication): AppComponent =
        DaggerAppComponent.builder()
            .productionModule(ProductionModule(app))
            .build()
}