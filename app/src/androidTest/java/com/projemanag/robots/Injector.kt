package com.projemanag.robots

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.auth.FirebaseAuth
import com.projemanag.MyCustomComponentBuilder
import com.projemanag.factory.UserFactory
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Inject

class Injector
@Inject
constructor(
    val componentBuilder: MyCustomComponentBuilder
){

    constructor()

    var userFactory: UserFactory

    var firebaseAuth: FirebaseAuth

    var context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @EntryPoint
    @InstallIn(ApplicationComponent::class)
    interface MyClassInterface {
        fun getFirebaseAuth(): FirebaseAuth

        fun getUserFactory(): UserFactory
    }

    init {
//        val component = TestBaseApplication_Application.builder()
//
//        component.inject(this)

        val component = componentBuilder.build()

        val myClassInterface =
            EntryPoints.get(component, MyClassInterface::class.java)
        userFactory = myClassInterface.getUserFactory()
        firebaseAuth = myClassInterface.getFirebaseAuth()
    }
}