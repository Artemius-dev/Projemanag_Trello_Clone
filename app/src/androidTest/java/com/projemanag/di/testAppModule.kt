package com.projemanag.di

import com.projemanag.firebase.FirestoreValues
import com.projemanag.firebase.IFirestoreClass
import com.projemanag.mockwebserver.MockServer
import io.mockk.mockk
import okhttp3.mockwebserver.MockWebServer
import org.koin.dsl.module

val testAppModule = module(override = true) {

    // single instance of HelloRepository
    single<IFirestoreClass>(override = true) { mockk() }

//    // Simple Presenter Factory
    factory(override = true) { FirestoreValues(get()) }
}

val mockWebServerModule = module(override = true) {
    single<MockWebServer>(override = true){ MockServer().provideMockServer() }

    factory(override = true) { MockServer().provideMockWebServerAndHost() }
}