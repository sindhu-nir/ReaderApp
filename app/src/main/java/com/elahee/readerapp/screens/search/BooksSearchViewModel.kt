package com.elahee.readerapp.screens.search

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bawp.freader.data.Resource
import com.elahee.readerapp.data.DataOrException
import com.elahee.readerapp.model.Item
import com.elahee.readerapp.repository.BookRepository
import com.elahee.readerapp.repository.BookRepository_
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksSearchViewModel @Inject constructor(private val repository: BookRepository) :
    ViewModel() {

    var list: List<Item> by mutableStateOf(listOf())
    var isLoading: Boolean by mutableStateOf(true)

    init {
        loadBooks()
    }

    private fun loadBooks() {
        searchBooks("flutter")
    }

    fun searchBooks(query: String) {
        isLoading=true
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                return@launch
            }
            try {
                when (val response = repository.getBooks(query)) {
                    is Resource.Success -> {
                        list = response.data!!
                        if (list.isNotEmpty()) isLoading = false
                    }

                    is Resource.Error -> {
                        isLoading = false
                        Log.e("Network", "searchBooks: Failed getting books")
                    }

                    else -> {
                        isLoading = false
                    }
                }

            } catch (exception: Exception) {
                isLoading = false
                Log.d("Network", "searchBooks: ${exception.message.toString()}")
            }

        }


    }


//        val listOfBooks: MutableState<DataOrException<List<Item>, Boolean, Exception>> =
//        mutableStateOf(DataOrException(null, true, Exception("")))
//
//    init {
//        searchBooks("android")
//    }
//
//    fun searchBooks(searchQuery: String) {
//        viewModelScope.launch {
//            if (searchQuery.isNullOrEmpty()) {
//                return@launch
//            }
//            listOfBooks.value.loading=true
//            listOfBooks.value= repository.getBooks(searchQuery)
//            Log.d("BooksSearchViewModel", "searchBooks: ${listOfBooks.value.data}")
//            if (listOfBooks.value.data.toString().isNullOrEmpty()) listOfBooks.value.loading= false
//        }
//    }
}