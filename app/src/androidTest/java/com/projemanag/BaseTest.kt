package com.projemanag

import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import com.google.firebase.auth.FirebaseAuth
import com.projemanag.di.DaggerAppComponent
import com.projemanag.di.DaggerTestAppComponent
import com.projemanag.di.TestModule
import com.projemanag.factroy.UserFactory
import com.projemanag.testHelpers.readJSONFromAsset
import com.projemanag.utils.EspressoIdlingResource
import com.schibsted.spain.barista.rule.cleardata.ClearDatabaseRule
import com.schibsted.spain.barista.rule.cleardata.ClearFilesRule
import com.schibsted.spain.barista.rule.cleardata.ClearPreferencesRule
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

abstract class BaseTest {

    init {
        val component = DaggerTestAppComponent.builder()
            .testModule(TestModule(BaseApplication())).build()

        component.inject(this)
    }

    @Before
    open fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource().countingIdlingResource)
    }

    @After
    fun teardown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource().countingIdlingResource)
    }

    @get: Rule
    var clearPreferencesRule = ClearPreferencesRule()

    @get: Rule
    var clearDatabaseRule = ClearDatabaseRule()

    @get: Rule
    var clearFilesRule = ClearFilesRule()



    companion object {
        const val EMAIL = "antonio@gmail.com"
        const val PASSWORD = "123456"
    }

    abstract fun injectTest()

}