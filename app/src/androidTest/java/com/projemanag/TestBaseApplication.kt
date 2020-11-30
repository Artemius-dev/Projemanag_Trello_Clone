package com.projemanag

import com.projemanag.di.DaggerTestAppComponent


class TestBaseApplication : BaseApplication() {

    override fun initAppComponent() {
        appComponent = DaggerTestAppComponent.factory()
            .create(this)
    }
}