package com.bawp.freader.screens.details

import androidx.lifecycle.ViewModel
import com.bawp.freader.data.Resource
import com.elahee.readerapp.model.Item
import com.elahee.readerapp.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: BookRepository)
    : ViewModel(){

        suspend fun getBookInfo(bookId: String): Resource<Item> {
            return repository.getBookInfo(bookId)
        }
    }