package com.ameen.newsapp.repository

import com.ameen.newsapp.data.local.ArticleDatabase
import com.ameen.newsapp.data.model.Article
import com.ameen.newsapp.data.network.ApiSettings
import retrofit2.Retrofit

class NewsRepository(
    val db: ArticleDatabase
) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        ApiSettings.apiInstance.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        ApiSettings.apiInstance.searchNews(searchQuery, pageNumber)

    //Room functions
    suspend fun saveRoomArticle(article: Article) =
        db.getArticleDao().insertArticle(article)

    suspend fun deleteRoomArticle(article: Article) =
        db.getArticleDao().deleteArticle(article)

    fun getAllSavedRoomArticle() = db.getArticleDao().getSavedArticles()
}