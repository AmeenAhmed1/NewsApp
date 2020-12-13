package com.ameen.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ameen.newsapp.R
import com.ameen.newsapp.data.local.ArticleDatabase
import com.ameen.newsapp.repository.NewsRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Setting the bottom navigation with NavGraph
        bottomNavigation.setupWithNavController(NavHostFragment.findNavController())

        // init ViewModel
        val repository = NewsRepository(ArticleDatabase(this))
        val viewModelProvider = NewsViewModelProvider(repository)
        viewModel = ViewModelProvider(this, viewModelProvider).get(NewsViewModel::class.java)
    }
}