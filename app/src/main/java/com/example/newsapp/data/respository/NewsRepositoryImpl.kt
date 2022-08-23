package com.example.newsapp.data.respository

import android.content.Context
import com.example.newsapp.data.api.ApiService
import com.example.newsapp.data.db.AppDatabase
import com.example.newsapp.data.db.NewEntity
import com.example.newsapp.data.model.DataResult
import com.example.newsapp.presentation.items.NewItem

class NewsRepositoryImpl(
    private val context: Context,
    private val api: ApiService
) : NewsRepository {

    private val apiKey = "3754822edc6d493a83d1766ab35385cf"
    private val country = "ua"
    private val db = AppDatabase.getDatabase(context.applicationContext)

    override suspend fun getNews(query: String): DataResult {
        return api.getNews(query, country, apiKey)
    }

    override suspend fun getSavedNews(): List<NewItem> {
        return db.dao().getAll().map {
            NewItem(
                iconUrl = it.iconUrl,
                title = it.title,
                subtitle = it.subtitle,
                web = it.web,
                webShare = it.webShare
            )
        }
    }

    override suspend fun saveNew(newItem: NewItem) {
        db.dao().insertAll(
            NewEntity(
                iconUrl = newItem.iconUrl,
                title = newItem.title,
                subtitle = newItem.subtitle,
                web = newItem.web,
                webShare = newItem.webShare
            )
        )
    }

    override suspend fun deleteNew(newItem: NewItem) {
        db.dao().delete(
            NewEntity(
                iconUrl = newItem.iconUrl,
                title = newItem.title,
                subtitle = newItem.subtitle,
                web = newItem.web,
                webShare = newItem.webShare
            )
        )
    }
}