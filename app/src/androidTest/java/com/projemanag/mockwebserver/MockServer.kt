package com.projemanag.mockwebserver

import dagger.Provides
import io.fabric8.mockwebserver.DefaultMockServer
import okhttp3.mockwebserver.MockWebServer
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Singleton

class MockServer {
    fun provideMockServer (): MockWebServer {
        var mockWebServer:MockWebServer? = null
        val thread = Thread(Runnable {
            mockWebServer = MockWebServer()
            mockWebServer?.start()
        })
        thread.start()
        thread.join()
        return mockWebServer ?: throw NullPointerException()
    }

    fun provideBaseUrl (mockWebServer:MockWebServer): String {
        var url = ""
        val t = Thread(Runnable {
            url = mockWebServer.url("/").toString()
        })
        t.start()
        t.join()
        return url
    }

    fun provideBaseUrl(mockWebServerAndHost: MockWebServerAndHost) = mockWebServerAndHost.baseUrl

    fun provideMockWebServer(
        mockWebServerAndHost: MockWebServerAndHost
    ) = mockWebServerAndHost.mockServer

    fun provideMockWebServerAndHost(): MockWebServerAndHost {
        val mockServer = DefaultMockServer()
        val baseUrlHolder = AtomicReference<String>()
        val startThread = Thread(Runnable {
            mockServer.start()

            // hostName and port cannot be called on main thread, so call them in a separate thread
            baseUrlHolder.set("http://${mockServer.hostName}:${mockServer.port}")
        }, "MockWebServer starter")
        with(startThread) {
            isDaemon = true
            start()
            join()
        }
        return MockWebServerAndHost(
            mockServer,
            baseUrlHolder.get()
        )
    }
}

data class MockWebServerAndHost(
    val mockServer: DefaultMockServer,
    val baseUrl: String
)
