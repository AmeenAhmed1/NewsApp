package com.ameen.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ameen.newsapp.R
import com.ameen.newsapp.data.local.ArticleDatabase
import com.ameen.newsapp.databinding.ActivityMainBinding
import com.ameen.newsapp.repository.NewsRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_main)

        //Setting the bottom navigation with NavGraph
        bottomNavigation.setupWithNavController(NavHostFragment.findNavController())

        // init ViewModel
        val repository = NewsRepository(ArticleDatabase(this))
        val viewModelProvider = NewsViewModelProvider(repository)
        viewModel = ViewModelProvider(this, viewModelProvider).get(NewsViewModel::class.java)
    }
}