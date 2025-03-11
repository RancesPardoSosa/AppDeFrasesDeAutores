package com.example.examplemvvm.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.examplemvvm.domain.GetQuotesUseCase
import com.example.examplemvvm.domain.GetRandomQuoteUseCase
import com.example.examplemvvm.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class QuoteViewModelTest {
    @RelaxedMockK
    private lateinit var getQuotesUseCase: GetQuotesUseCase

    @RelaxedMockK
    private lateinit var getRandomQuoteUseCase: GetRandomQuoteUseCase

    private lateinit var quoteViewModel: QuoteViewModel

    //core
    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    //

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        quoteViewModel = QuoteViewModel(getQuotesUseCase, getRandomQuoteUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewmodel is created at the first time, get all quotes and set the first value`() {
        runTest {
            //Given

            val listQuotes = listOf(Quote("dasdasd", "rances"), Quote("cita prueba", "prueba"))
            coEvery { getQuotesUseCase() } returns listQuotes

            //When

            quoteViewModel.onCreate()

            //Then

            assert(quoteViewModel.quoteModel.value == listQuotes.first())
        }
    }

    @Test
    fun `when randomQuoteUseCase return a quote set on the livedata`() {
        runTest {
            //Given

            val quote = Quote("lorem ipsum", "Lorem")
            coEvery { getRandomQuoteUseCase() } returns quote

            //When

            quoteViewModel.randomQuote()

            //Then

            assert(quoteViewModel.quoteModel.value == quote)

        }
    }

    @Test
    fun `if randomQuoteUseCase return null keep the last value`() {
        runTest {
            //Given

            val quote = Quote("Lorem ipsun","Lorem")
            quoteViewModel.quoteModel.value = quote
            coEvery { getRandomQuoteUseCase() } returns null

            //When

            quoteViewModel.randomQuote()

            //Then

            assert(quoteViewModel.quoteModel.value == quote)

        }
    }


}