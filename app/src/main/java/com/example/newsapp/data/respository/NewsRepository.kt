package com.example.newsapp.data.respository

import com.example.newsapp.data.model.DataResult
import com.example.newsapp.presentation.items.NewItem

interface NewsRepository {

    suspend fun getNews(query: String = ""): DataResult

    suspend fun getSavedNews():List<NewItem>

    suspend fun saveNew(newItem: NewItem)

    suspend fun deleteNew(newItem: NewItem)
}