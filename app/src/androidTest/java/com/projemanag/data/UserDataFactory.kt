package com.projemanag.data

import android.app.Application
import com.projemanag.models.factory.UserFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataFactory
@Inject
constructor(
    private val application: Application,
    private val noteFactory: UserFactory
)