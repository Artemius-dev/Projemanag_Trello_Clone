package com.projemanag.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.projemanag.models.factory.UserFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ProductionModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideUserFactory(firebaseAuth: FirebaseAuth): UserFactory {
        return UserFactory(firebaseAuth)
    }

}