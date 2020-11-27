package com.projemanag.di

import com.projemanag.firebase.FirestoreClass
import com.projemanag.firebase.FirestoreValues
import com.projemanag.firebase.IFirestoreClass
import org.koin.dsl.module

val appModule = module {

    // single instance of HelloRepository
    single<IFirestoreClass>(override = true) { FirestoreClass() }

    // Simple Presenter Factory
    factory(override = true)  { FirestoreValues(get()) }
}