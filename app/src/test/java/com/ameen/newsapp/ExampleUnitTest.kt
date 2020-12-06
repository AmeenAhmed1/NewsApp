package com.ameen.newsapp

import com.ameen.newsapp.data.network.ApiSettings
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    suspend fun `getAllNews()` (){
        val news = ApiSettings.apiInstance
        val result = news.getBreakingNews().body()!!.status

        assertEquals("ok", result)
    }
}