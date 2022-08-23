package com.example.newsapp.presentation.mappers

import com.example.newsapp.data.model.Articles
import com.example.newsapp.presentation.items.NewItem

object NewsMapper {

    fun toNewItem(articles: Articles): NewItem {
        return NewItem(
            iconUrl = articles.urlToImage ?: articles.url,
            title = articles.title,
            subtitle = articles.content,
            web = articles.source.name,
            webShare = articles.url


        )
    }
}