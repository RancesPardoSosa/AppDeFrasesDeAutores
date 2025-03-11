package com.example.examplemvvm.data

import com.example.examplemvvm.data.database.dao.QuoteDao
import com.example.examplemvvm.data.database.entities.QuoteEntity
import com.example.examplemvvm.data.model.QuoteModel
import com.example.examplemvvm.data.model.QuoteProvider
import com.example.examplemvvm.data.network.QuoteService
import com.example.examplemvvm.domain.model.Quote
import com.example.examplemvvm.domain.model.toDomain
import javax.inject.Inject

class QuoteRepository @Inject constructor(
    private val quoteService: QuoteService,
    private val quoteDao: QuoteDao
) {

    suspend fun getAllQuotesFromAPI(): List<Quote> {
        val response = quoteService.getQuotes()
        return response.map { it.toDomain() }
    }

    suspend fun getAllQuotesFromDatabase(): List<Quote> {
        val response = quoteDao.getAllQuotesEntities()
        return response.map { it.toDomain() }
    }

    suspend fun insertAllQuotes(quotesEntities: List<QuoteEntity>){
        quoteDao.insertAllQuotesEntities(quotesEntities)
    }

    suspend fun clearDatabase(){
        quoteDao.deleteAllQuotesEntities()
    }
}