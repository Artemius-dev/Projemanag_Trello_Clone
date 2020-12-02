package com.projemanag.di

import android.app.Application
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.projemanag.firebase.FirestoreClass
import com.projemanag.firebase.IFirestoreClass
import com.projemanag.models.factory.BoardFactory
import com.projemanag.models.factory.TaskFactory
import com.projemanag.models.factory.UserFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ProductionModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideUserFactory(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore,
        firebaseClass: FirestoreClass
    ): UserFactory {
        return UserFactory(firebaseAuth, firebaseClass)
    }

    @Singleton
    @Provides
    fun provideCardFactory(firestoreClass: FirestoreClass): TaskFactory {
        return TaskFactory(firestoreClass)
    }

    @Singleton
    @Provides
    fun provideFirestoreClass(
        firebase: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ): IFirestoreClass {
        return FirestoreClass(firebase, firebaseAuth)
    }

    @Singleton
    @Provides
    fun provideBoardFactory(firestoreClass: FirestoreClass): BoardFactory {
        return BoardFactory(firestoreClass)
    }

}