package com.example.newsapp.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.respository.NewsRepository
import com.example.newsapp.presentation.items.NewItem
import com.example.newsapp.presentation.mappers.NewsMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    private val _news = MutableStateFlow<State>(State.Loading)
    val news: StateFlow<State> = _news

    fun getNews(query: String = "") {
        viewModelScope.launch {
            val news = withContext(Dispatchers.IO) {
                val result = repository.getNews(query)
                result.articles.map {
                    NewsMapper.toNewItem(it)
                }
            }
            _news.value = State.Success(news)
        }
    }

    fun saveNew(newItem: NewItem) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.saveNew(newItem)
            }
        }

    }



    sealed class State {
        object Loading : State()
        data class Success(val result: List<NewItem>) : State()
    }
}