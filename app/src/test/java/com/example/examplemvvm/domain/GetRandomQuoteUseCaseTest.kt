package com.example.examplemvvm.domain

import com.example.examplemvvm.data.QuoteRepository
import com.example.examplemvvm.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetRandomQuoteUseCaseTest {
    @RelaxedMockK
    private lateinit var quoteRepository: QuoteRepository
    private lateinit var getRandomQuoteUseCase: GetRandomQuoteUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getRandomQuoteUseCase = GetRandomQuoteUseCase(quoteRepository)
    }

    @Test
    fun `when database is empty then return null`() {
        runBlocking {
            //Given

            coEvery { quoteRepository.getAllQuotesFromDatabase() } returns emptyList()

            //When

            val response = getRandomQuoteUseCase()

            //Then

            assert(response == null)
        }
    }

    @Test
    fun `when database is not empty then return quote random`() {
        runBlocking {
            //Given
            val quotes:List<Quote> = listOf(Quote("dasdasd","rances"))

            coEvery { quoteRepository.getAllQuotesFromDatabase() } returns quotes

            //When
            val response = getRandomQuoteUseCase()

            //Then
            assert(response == quotes.first())

        }
    }

}