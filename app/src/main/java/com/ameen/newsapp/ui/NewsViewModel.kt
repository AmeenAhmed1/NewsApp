package com.ameen.newsapp.ui

import androidx.lifecycle.ViewModel
import com.ameen.newsapp.repository.NewsRepository

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {
}