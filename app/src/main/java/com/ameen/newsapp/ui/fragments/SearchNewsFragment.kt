package com.ameen.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ameen.newsapp.R
import com.ameen.newsapp.adapter.NewsAdapter
import com.ameen.newsapp.databinding.FragmentSearchNewsBinding
import com.ameen.newsapp.ui.MainActivity
import com.ameen.newsapp.ui.NewsViewModel
import com.ameen.newsapp.util.ResponseWrapper
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

    private val TAG = "SearchNewsFragment"

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    private var _binding: FragmentSearchNewsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchNewsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        setRecycler()

        newsAdapter.onItemClicked {
            val bundle = Bundle()
            bundle.putSerializable("selectedArticle", it)

            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment,
                bundle
            )
        }

        var job: Job? = null
        binding.etSearch.addTextChangedListener {
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
        if (state) binding.paginationProgressBar.visibility = View.VISIBLE
        else binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun setRecycler() {
        newsAdapter = NewsAdapter()
        binding.rvSearchNews?.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}