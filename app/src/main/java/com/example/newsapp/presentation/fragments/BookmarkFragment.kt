package com.example.newsapp.presentation.fragments

import android.os.Bundle
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
import com.example.newsapp.databinding.FragmentBookmarkBinding
import com.example.newsapp.presentation.adapters.RecycleAdapter
import com.example.newsapp.presentation.items.NewItem
import com.example.newsapp.presentation.utils.viewBinding
import com.example.newsapp.presentation.viewModels.BookmarkViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookmarkFragment : Fragment(R.layout.fragment_bookmark) {

    private val binding by viewBinding(FragmentBookmarkBinding::bind)

    private val viewModel: BookmarkViewModel by viewModel()

    private var adapter: RecycleAdapter = RecycleAdapter(
        onClick = {
            val action = NavGraphDirections.actionToWebViewFragment(it.webShare.orEmpty())
            findNavController().navigate(action)
        },
        onSaveClick = { viewModel.deleteNew(it) },
        onShareClick = { shareNew(it) }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initViews()
        initListeners()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSavedNews()
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.news.collect { state ->
                when (state) {
                    is BookmarkViewModel.State.Success -> {
                        showLoading(false)
                        adapter.submitList(state.result)
                    }
                    BookmarkViewModel.State.Loading -> showLoading(true)
                }
            }
        }
    }

    private fun initViews() {
        binding.rvBookmarks.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@BookmarkFragment.adapter
        }
        binding.svBookmarks.queryHint = "Search"
    }

    private fun showLoading(isVisible: Boolean) {
        binding.loading.isVisible = isVisible
    }

    private fun initListeners() {
        binding.svBookmarks.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
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