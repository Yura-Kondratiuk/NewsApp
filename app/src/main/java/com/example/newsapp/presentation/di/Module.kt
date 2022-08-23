package com.example.newsapp.presentation.di

import com.example.newsapp.data.api.ApiService
import com.example.newsapp.data.respository.NewsRepository
import com.example.newsapp.data.respository.NewsRepositoryImpl
import com.example.newsapp.presentation.viewModels.BookmarkViewModel
import com.example.newsapp.presentation.viewModels.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val mainModule = module {
    single<NewsRepository> {
        NewsRepositoryImpl(get(), get())
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
    viewModel {
        NewsViewModel(get())
    }
    viewModel{
        BookmarkViewModel(get())
    }
}