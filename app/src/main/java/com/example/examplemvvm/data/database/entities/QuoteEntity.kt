package com.example.examplemvvm.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.examplemvvm.data.model.QuoteModel
import com.example.examplemvvm.domain.model.Quote

@Entity(tableName = "quote_table")
data class QuoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val quote: String,
    val author: String
)

fun Quote.toEntity() = QuoteEntity(quote = quote, author = author)