package com.projemanag

import androidx.test.espresso.intent.Intents
import androidx.test.rule.GrantPermissionRule
import com.projemanag.di.mockWebServerModule
import com.projemanag.di.testAppModule
import com.projemanag.firebase.IFirestoreClass
import com.projemanag.mockwebserver.MockWebServerAndHost
import com.projemanag.testHelpers.readJSONFromAsset
import io.fabric8.mockwebserver.DefaultMockServer
import okhttp3.mockwebserver.MockWebServer
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

open class MockTest : KoinTest {
    val permissionRule: GrantPermissionRule =
            GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION)


    val testScope: MockTest by lazy { this }
    val firestoreScope: IFirestoreClass by inject()
    val mockWebServer : DefaultMockServer by inject()

    open fun setUp() {
        stopKoin()
        startKoin {
            modules(testAppModule, mockWebServerModule)
        }
        Intents.init()
    }

    fun getMock(
        endPoint: String,
        statusCode: Int,
        filePath: String,
        repeat: Int = 1,
        always: Boolean = false
    ) {
        if (always) {
            mockWebServer.expect()
                .get()
                .withPath(endPoint)
                .andReturn(statusCode, readJSONFromAsset(filePath))
                .always()
        } else {
            mockWebServer.expect()
                .get()
                .withPath(endPoint)
                .andReturn(statusCode, readJSONFromAsset(filePath))
                .times(repeat)
        }
    }

    fun stubOk(endPoint: String) {
        mockWebServer.expect()
            .get()
            .withPath(endPoint)
            .andReturn(200, "")
            .always()
    }

    fun postMock(endPoint: String, statusCode: Int, filePath: String, repeat: Int = 1) {
        mockWebServer.expect()
            .post()
            .withPath(endPoint)
            .andReturn(statusCode, readJSONFromAsset(filePath))
            .times(repeat)
    }

    open fun tearDown() {
        Intents.release()
    }
}