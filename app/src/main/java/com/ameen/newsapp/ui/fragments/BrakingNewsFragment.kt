package com.ameen.newsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ameen.newsapp.R
import com.ameen.newsapp.ui.MainActivity
import com.ameen.newsapp.ui.NewsViewModel

class BrakingNewsFragment : Fragment(R.layout.fragment_braking_news) {

    lateinit var viewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }
}