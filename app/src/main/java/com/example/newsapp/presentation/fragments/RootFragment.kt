package com.example.newsapp.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentRootBinding
import com.example.newsapp.presentation.adapters.ViewPagerAdapter
import com.example.newsapp.presentation.utils.viewBinding
import com.google.android.material.tabs.TabLayoutMediator

class RootFragment : Fragment(R.layout.fragment_root) {

    private val binding by viewBinding(FragmentRootBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.icon = AppCompatResources.getDrawable(
                requireContext(),
                if (position == 0) R.drawable.ic_home
                else R.drawable.ic_save
            )
        }.attach()

    }
}