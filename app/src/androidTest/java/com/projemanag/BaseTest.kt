package com.projemanag

import androidx.test.espresso.IdlingRegistry
import com.projemanag.utils.EspressoIdlingResource
import com.schibsted.spain.barista.rule.cleardata.ClearDatabaseRule
import com.schibsted.spain.barista.rule.cleardata.ClearFilesRule
import com.schibsted.spain.barista.rule.cleardata.ClearPreferencesRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

open class BaseTest {

    @Before
    fun setup() {
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

}