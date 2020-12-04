package com.projemanag.testHelpers

import android.app.Activity
import androidx.test.espresso.intent.Intents
import androidx.test.rule.ActivityTestRule

class ExhaustiveIntentsTestRule<T : Activity> : ActivityTestRule<T> {

    private var isInitialized: Boolean = false

    constructor(activityClass: Class<T>) : super(activityClass)

    constructor(activityClass: Class<T>, initialTouchMode: Boolean) : super(activityClass, initialTouchMode)

    constructor(activityClass: Class<T>, initialTouchMode: Boolean, launchActivity: Boolean) : super(
        activityClass,
        initialTouchMode,
        launchActivity
    )

    override fun beforeActivityLaunched() {
        super.beforeActivityLaunched()
        Intents.init()
        isInitialized = true
    }

    override fun afterActivityFinished() {
        super.afterActivityFinished()
        if (isInitialized) {
            // Otherwise will throw a NPE if Intents.init() wasn't called.
            Intents.release()
            isInitialized = false
        }
    }
}