package com.example.newsapp.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentWebViewBinding
import com.example.newsapp.presentation.utils.viewBinding

class WebViewFragment : DialogFragment(R.layout.fragment_web_view) {

    private val binding by viewBinding(FragmentWebViewBinding::bind)

    private val args: WebViewFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.wvScreen.loadUrl(args.webShare)
        initListeners()
    }

    private fun initListeners() {
        binding.toolbar.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}