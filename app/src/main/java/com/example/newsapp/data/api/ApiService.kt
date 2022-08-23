package com.example.newsapp.data.api

import com.example.newsapp.data.model.DataResult
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("country") country: String,
        @Query("apiKey") apiKey: String,
    ): DataResult
}