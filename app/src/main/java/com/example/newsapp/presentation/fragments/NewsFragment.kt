package com.example.newsapp.presentation.fragments


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.core.app.ShareCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.NavGraphDirections
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.presentation.adapters.RecycleAdapter
import com.example.newsapp.presentation.items.NewItem
import com.example.newsapp.presentation.utils.viewBinding
import com.example.newsapp.presentation.viewModels.NewsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment : Fragment(R.layout.fragment_news) {

    private val binding by viewBinding(FragmentNewsBinding::bind)

    private val viewModel: NewsViewModel by viewModel()

    private var adapter: RecycleAdapter = RecycleAdapter(
        onClick = {
            val action = NavGraphDirections.actionToWebViewFragment(it.webShare.orEmpty())
            findNavController().navigate(action)
        },
        onSaveClick = { viewModel.saveNew(it) },
        onShareClick = { shareNew(it) }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initViews()
        initListeners()
        viewModel.getNews()
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.news.collect { state ->
                when (state) {
                    is NewsViewModel.State.Success -> {
                        showLoading(false)
                        adapter.submitList(state.result)
                    }
                    NewsViewModel.State.Loading -> showLoading(true)
                }
            }
        }
    }

    private fun initViews() {
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@NewsFragment.adapter
        }
        binding.svNews.queryHint = "Search"
    }

    private fun showLoading(isVisible: Boolean) {
        binding.loading.isVisible = isVisible
    }

    private fun initListeners() {
        binding.svNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                Log.e("LOGS", p0.toString())
                viewModel.getNews(p0.orEmpty())
                return true
            }
        })
    }

    private fun shareNew(newItem: NewItem) {
        activity?.let { it1 ->
            ShareCompat.IntentBuilder
                .from(it1)
                .setType("text/plain")
                .setChooserTitle("Share URL")
                .setText(newItem.webShare)
                .startChooser()
        }
    }
}