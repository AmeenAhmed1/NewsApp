package com.ameen.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ameen.newsapp.R
import com.ameen.newsapp.adapter.NewsAdapter
import com.ameen.newsapp.ui.MainActivity
import com.ameen.newsapp.ui.NewsViewModel
import com.ameen.newsapp.util.ResponseWrapper
import kotlinx.android.synthetic.main.fragment_braking_news.*

class BrakingNewsFragment : Fragment(R.layout.fragment_braking_news) {

    private val TAG = "BrakingNewsFragment"

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setNewsRecycler()

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer {
            when (it) {

                is ResponseWrapper.Success -> {
                    isProgressbarActive(false)
                    it.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }

                is ResponseWrapper.Error -> {
                    isProgressbarActive(false)
                    it.message?.let {
                        Log.e(TAG, "onViewCreated: $it" )
                    }
                }

                is ResponseWrapper.Loading -> isProgressbarActive(true)

            }
        })
    }

    private fun setNewsRecycler() {
        newsAdapter = NewsAdapter()
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun isProgressbarActive(state: Boolean) {
        if (state) paginationProgressBar.visibility = View.VISIBLE
        else paginationProgressBar.visibility = View.INVISIBLE
    }
}