package com.projemanag.di

import com.azimolabs.conditionwatcher.ConditionWatcher
import com.projemanag.BaseTest
import com.projemanag.conditionwatcher.ConditionWatchers
import com.projemanag.tests.IntroScreenTest
import com.projemanag.robots.BaseTestRobot
import com.projemanag.tests.SignInScreenTest
import com.projemanag.tests.SignUpScreenTest
import com.projemanag.tests.SplashActivityTest
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@FlowPreview
@Singleton
@Component(
    modules = [
        TestModule::class
//        ActivityBuildersModule::class,
//        AndroidSupportInjectionModule::class
    ]
)
interface TestAppComponent {

//    @Component.Builder
//    interface Builder {
//        @BindsInstance
//        fun application(application: Application?): Builder?
//        fun build(): TestAppComponent?
//    }
//
//    fun inject(instance: DaggerApplication?)
//
//    override fun inject(app: TestBaseApplication)
//
//    fun getUserFactory(): UserFactory

    fun inject(target: SplashActivityTest)

    fun inject(target: IntroScreenTest)

    fun inject(target: SignInScreenTest)

    fun inject(target: SignUpScreenTest)

    fun inject(target: BaseTest)

    fun inject(target: BaseTestRobot)
}