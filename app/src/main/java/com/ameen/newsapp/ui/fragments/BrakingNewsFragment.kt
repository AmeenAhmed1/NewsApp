package com.ameen.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ameen.newsapp.R
import com.ameen.newsapp.adapter.NewsAdapter
import com.ameen.newsapp.databinding.FragmentBreakingNewsBinding
import com.ameen.newsapp.ui.MainActivity
import com.ameen.newsapp.ui.NewsViewModel
import com.ameen.newsapp.util.ResponseWrapper

class BrakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    private val TAG = "BrakingNewsFragment"

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    private var _binding: FragmentBreakingNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setNewsRecycler()

        newsAdapter.onItemClicked {
            val bundle = Bundle()
            bundle.putSerializable("selectedArticle", it)

            findNavController().navigate(
                R.id.action_brakingNewsFragment_to_articleFragment,
                bundle
            )
        }

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
                        Log.e(TAG, "onViewCreated: $it")
                    }
                }

                is ResponseWrapper.Loading -> isProgressbarActive(true)

            }
        })
    }

    private fun setNewsRecycler() {
        newsAdapter = NewsAdapter()
        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun isProgressbarActive(state: Boolean) {
        if (state) binding.paginationProgressBar.visibility = View.VISIBLE
        else binding.paginationProgressBar.visibility = View.INVISIBLE
    }
}