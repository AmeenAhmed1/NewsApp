package com.ameen.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ameen.newsapp.R
import com.ameen.newsapp.adapter.NewsAdapter
import com.ameen.newsapp.ui.MainActivity
import com.ameen.newsapp.ui.NewsViewModel
import com.ameen.newsapp.util.ResponseWrapper
import kotlinx.android.synthetic.main.fragment_braking_news.*
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.android.synthetic.main.fragment_search_news.paginationProgressBar
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

    private val TAG = "SearchNewsFragment"

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        setRecycler()

        var job: Job? = null
        etSearch.addTextChangedListener {
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.searchNews(it.toString())
                    }
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResponseWrapper.Loading -> isProgressbarActive(true)

                is ResponseWrapper.Success -> {
                    isProgressbarActive(false)
                    it.data?.let { response ->
                        newsAdapter.differ.submitList(response.articles)
                    }
                }

                is ResponseWrapper.Error -> {
                    isProgressbarActive(false)
                    it.message?.let {
                        Log.e(TAG, "onViewCreated: $it")
                    }
                }
            }
        })
    }

    private fun isProgressbarActive(state: Boolean) {
        if (state) paginationProgressBar.visibility = View.VISIBLE
        else paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun setRecycler() {
        newsAdapter = NewsAdapter()
        rvSearchNews?.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}