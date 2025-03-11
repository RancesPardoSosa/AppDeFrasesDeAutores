package com.example.examplemvvm.domain

import com.example.examplemvvm.data.QuoteRepository
import com.example.examplemvvm.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetQuotesUseCaseTest {
    @RelaxedMockK
    private lateinit var quoteRepository: QuoteRepository

    private lateinit var getQuotesUseCase: GetQuotesUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getQuotesUseCase = GetQuotesUseCase(quoteRepository)
    }

    @Test
    fun `When the api returns empty then it returns the values from the database`(){
        runBlocking {
            //Given

            coEvery { quoteRepository.getAllQuotesFromAPI() } returns emptyList()

            //When

            getQuotesUseCase()

            //Then

            coVerify(exactly = 1) { quoteRepository.getAllQuotesFromDatabase() }
            coVerify(exactly = 0) { quoteRepository.clearDatabase() }
            coVerify(exactly = 0) { quoteRepository.insertAllQuotes(any()) }
        }
    }

    @Test
    fun `when the api does not return empty then it gets a list of quotes`(){
        runBlocking {
            //Given

            val quotes: List<Quote> = listOf(Quote("dasdasd","rances"))

            coEvery { quoteRepository.getAllQuotesFromAPI() } returns quotes

            //When

            val response = getQuotesUseCase()

            //Then

            assert(response == quotes)
            coVerify(exactly = 0) { quoteRepository.getAllQuotesFromDatabase() }
            coVerify(exactly = 1) { quoteRepository.clearDatabase() }
            coVerify(exactly = 1) { quoteRepository.insertAllQuotes(any()) }
        }
    }
}