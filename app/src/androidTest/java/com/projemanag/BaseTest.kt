package com.projemanag

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.intent.Intents
import com.projemanag.utils.EspressoIdlingResource
import com.schibsted.spain.barista.rule.cleardata.ClearDatabaseRule
import com.schibsted.spain.barista.rule.cleardata.ClearFilesRule
import com.schibsted.spain.barista.rule.cleardata.ClearPreferencesRule
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class BaseTest {

    init {
//        val component = DaggerTestAppComponent.builder()
//            .testModule(TestModule(BaseApplication())).build()
//
//        component.inject(this)
    }


//    @Before
//    open fun setup() {
//        IdlingRegistry.getInstance().register(EspressoIdlingResource().countingIdlingResource)
//        Intents.init()
//    }
//
//    @After
//    open fun teardown() {
//        Intents.release()
//        IdlingRegistry.getInstance().unregister(EspressoIdlingResource().countingIdlingResource)
//    }

//    @get: Rule
    var clearPreferencesRule = ClearPreferencesRule()

//    @get: Rule
    var clearDatabaseRule = ClearDatabaseRule()

//    @get: Rule
    var clearFilesRule = ClearFilesRule()

}