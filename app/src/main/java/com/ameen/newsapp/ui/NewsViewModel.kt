package com.ameen.newsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ameen.newsapp.data.model.NewsResponse
import com.ameen.newsapp.repository.NewsRepository
import com.ameen.newsapp.util.ResponseWrapper
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {

    val breakingNews: MutableLiveData<ResponseWrapper<NewsResponse>> = MutableLiveData()
    val breakingNewsPage = 1

    val searchNews: MutableLiveData<ResponseWrapper<NewsResponse>> = MutableLiveData()
    val searchNewsPage = 1

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
        val response =  newsRepository.searchNews(searchQuery, searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): ResponseWrapper<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return ResponseWrapper.Success(it)
            }
        }
        return ResponseWrapper.Error(message = response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): ResponseWrapper<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return ResponseWrapper.Success(it)
            }
        }
        return ResponseWrapper.Error(message = response.message())
    }
}