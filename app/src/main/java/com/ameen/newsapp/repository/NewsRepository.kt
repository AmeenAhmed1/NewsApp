package com.ameen.newsapp.repository

import com.ameen.newsapp.data.local.ArticleDatabase
import com.ameen.newsapp.data.network.ApiSettings
import retrofit2.Retrofit

class NewsRepository(
    val db: ArticleDatabase
) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        ApiSettings.apiInstance.getBreakingNews(countryCode, pageNumber)

}