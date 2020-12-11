package com.ameen.newsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ameen.newsapp.repository.NewsRepository

class NewsViewModelProvider(
    val _newsRepository: NewsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(_newsRepository) as T
    }
}