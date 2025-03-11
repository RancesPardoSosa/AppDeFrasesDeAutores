package com.example.examplemvvm.domain

import com.example.examplemvvm.data.QuoteRepository
import com.example.examplemvvm.data.database.entities.toEntity
import com.example.examplemvvm.data.model.QuoteModel
import com.example.examplemvvm.domain.model.Quote
import javax.inject.Inject

class GetQuotesUseCase @Inject constructor(
    private val repository: QuoteRepository
) {

    suspend operator fun invoke() :List<Quote> {
        val quotesFromAPI = repository.getAllQuotesFromAPI()
        return if (quotesFromAPI.isNotEmpty()){
            repository.clearDatabase()
            repository.insertAllQuotes(quotesFromAPI.map { it.toEntity() })
            quotesFromAPI
        }else{
            repository.getAllQuotesFromDatabase()
        }
    }

}