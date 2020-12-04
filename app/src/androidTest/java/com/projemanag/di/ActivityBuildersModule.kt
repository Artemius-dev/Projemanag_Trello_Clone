package com.projemanag.di

import com.projemanag.activities.CardDetailsActivity
import com.projemanag.activities.CreateBoardActivity
import com.projemanag.activities.IntroActivity
import com.projemanag.activities.MainActivity
import com.projemanag.activities.MembersActivity
import com.projemanag.activities.MyProfileActivity
import com.projemanag.activities.SignInActivity
import com.projemanag.activities.SignUpActivity
import com.projemanag.activities.SplashActivity
import com.projemanag.activities.TaskListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeCardDetailsActivity(): CardDetailsActivity?

    @ContributesAndroidInjector
    abstract fun contributeCreateBoardActivity(): CreateBoardActivity?

    @ContributesAndroidInjector
    abstract fun contributeIntroActivity(): IntroActivity?

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity?

    @ContributesAndroidInjector
    abstract fun contributeMembersActivity(): MembersActivity?

    @ContributesAndroidInjector
    abstract fun contributeMyProfileActivity(): MyProfileActivity?

    @ContributesAndroidInjector
    abstract fun contributeSignInActivity(): SignInActivity?

    @ContributesAndroidInjector
    abstract fun contributeSignUpActivity(): SignUpActivity?

    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity?

    @ContributesAndroidInjector
    abstract fun contributeTaskListActivity(): TaskListActivity?

}