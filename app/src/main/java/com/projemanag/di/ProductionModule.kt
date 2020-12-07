package com.projemanag.di

import android.app.Application
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.projemanag.BaseApplication
import com.projemanag.firebase.FirestoreClass
import com.projemanag.firebase.IFirestoreClass
import com.projemanag.models.factory.BoardFactory
import com.projemanag.models.factory.IUserFactory
import com.projemanag.models.factory.TaskFactory
import com.projemanag.models.factory.UserFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
open class ProductionModule() {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext appContext: Context): Context = appContext

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
    open fun provideUserFactory(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore,
        firebaseClass: FirestoreClass
    ): IUserFactory {
        return UserFactory(firebaseAuth, firebaseClass)
    }

    @Singleton
    @Provides
    open fun provideTaskFactory(firestoreClass: FirestoreClass): TaskFactory {
        return TaskFactory(firestoreClass)
    }

    @Singleton
    @Provides
    open fun provideFirestoreClass(
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