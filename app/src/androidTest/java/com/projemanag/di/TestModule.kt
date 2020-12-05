package com.projemanag.di

import android.app.Application
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.projemanag.factory.TaskFactory
import com.projemanag.factory.UserFactory
import com.projemanag.firebase.FirestoreClass
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

//@Module
//@InstallIn(ApplicationComponent::class)
class TestModule() {

//    @Provides
//    @Singleton
//    fun provideContext(@ApplicationContext appContext: Context): Context = appContext
//
//    @Singleton
//    @Provides
//    fun provideFirestoreSettings(): FirebaseFirestoreSettings {
//        return FirebaseFirestoreSettings.Builder()
//            .setHost("10.0.2.2:8080")
//            .setSslEnabled(false)
//            .setPersistenceEnabled(false)
//            .build()
//    }
//
//    @Singleton
//    @Provides
//    fun provideFirestoreAuth(): FirebaseAuth {
//        val firebaseAuth = FirebaseAuth.getInstance()
//        firebaseAuth.useEmulator("10.0.2.2", 9099)
//        return firebaseAuth
//    }
//
//    @Singleton
//    @Provides
//    fun provideFirebaseFirestore(settings: FirebaseFirestoreSettings): FirebaseFirestore {
//        val firestore = FirebaseFirestore.getInstance()
//
//        firestore.firestoreSettings = settings
//
//        return firestore
//    }
//
//    @Singleton
//    @Provides
//    fun provideFirestoreClass(firebaseFirestore: FirebaseFirestore, firebaseAuth: FirebaseAuth): FirestoreClass {
//        return FirestoreClass(firebaseFirestore, firebaseAuth)
//    }
//
//    @Singleton
//    @Provides
//    fun provideTaskFactory(firestoreClass: FirestoreClass): TaskFactory {
//        return TaskFactory(firestoreClass)
//    }
//
//    @Singleton
//    @Provides
//    fun provideUserFactory(firebaseAuth: FirebaseAuth, firebaseFirestore: FirebaseFirestore, firestoreClass: FirestoreClass): UserFactory {
//        return UserFactory(firebaseAuth, firebaseFirestore, firestoreClass)
//    }

}
