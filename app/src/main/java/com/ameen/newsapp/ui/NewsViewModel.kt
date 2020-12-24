package com.ameen.newsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ameen.newsapp.data.model.Article
import com.ameen.newsapp.data.model.NewsResponse
import com.ameen.newsapp.repository.NewsRepository
import com.ameen.newsapp.util.ResponseWrapper
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {

    val breakingNews: MutableLiveData<ResponseWrapper<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse: NewsResponse? = null

    val searchNews: MutableLiveData<ResponseWrapper<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: NewsResponse? = null

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(ResponseWrapper.Loading())
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(ResponseWrapper.Loading())
        val response = newsRepository.searchNews(searchQuery, searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): ResponseWrapper<NewsResponse> {
        if (response.isSuccessful) {
            breakingNewsPage++ //increase the page number every call
            response.body()?.let {
                if (breakingNewsResponse == null) breakingNewsResponse = it
                else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = it.articles
                    oldArticles?.addAll(newArticles)
                }
                return ResponseWrapper.Success(breakingNewsResponse ?: it)
            }
        }
        return ResponseWrapper.Error(message = response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): ResponseWrapper<NewsResponse> {
        if (response.isSuccessful) {
            searchNewsPage++
            response.body()?.let {
                if(searchNewsResponse == null) searchNewsResponse = it
                else{
                    val oldSearchArticle = searchNewsResponse?.articles
                    val newSearchArticle = it.articles
                    oldSearchArticle?.addAll(newSearchArticle)
                }
                return ResponseWrapper.Success(breakingNewsResponse ?: it)
            }
        }
        return ResponseWrapper.Error(message = response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.saveRoomArticle(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteRoomArticle(article)
    }

    fun getAllSavedArticles() = newsRepository.getAllSavedRoomArticle()
}