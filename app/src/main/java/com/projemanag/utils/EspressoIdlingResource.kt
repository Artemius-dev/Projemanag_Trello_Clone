package com.projemanag.utils

class EspressoIdlingResource {

    private val RESOURCE = "GLOBAL"

    @JvmField
    val countingIdlingResource
            = SimpleCountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }

}