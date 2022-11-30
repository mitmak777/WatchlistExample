package com.example.watchlistexample.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.watchlistexample.MainCoroutineRule
import com.example.watchlistexample.domain.model.ForexDetail
import com.example.watchlistexample.domain.ForexWatchlistUseCaseImpl
import com.example.watchlistexample.ui.viewmodel.ForexWatchlistViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.assertEquals
import java.math.BigDecimal
import java.util.*

class ForexWatchlistViewModelTest{

    private lateinit var viewModel: ForexWatchlistViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var useCase: ForexWatchlistUseCaseImpl

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = ForexWatchlistViewModel(useCase)

    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun test_updateFxPair_isLoadingReturnsTrue() = runTest {
        coEvery { useCase.getForexList(listOf("EURUSD")) } returns emptyFlow()
        viewModel.updateFxPair(listOf("EURUSD", "USDJPY"))
        coVerify(exactly = 1) { useCase.getForexList(listOf("EURUSD", "USDJPY")) }
        viewModel.isLoading.test {
            assertEquals(awaitItem(),true)
        }
    }

    @Test
    fun test_updateFxPair_fxListShouldRecieveUpdate() = runTest {
        val fxList = listOf(ForexDetail("EURUSD", BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, Date().time))
        coEvery { useCase.getForexList(listOf("EURUSD")) } returns
                flow {
                    emit(Result.success(fxList)) }
        coEvery { useCase.getEquity(any()) } returns Result.success(emptyList())

        viewModel.updateFxPair(listOf("EURUSD"))
        viewModel.fxListStateFlow.test {
            val nextItem2 = awaitItem()
            Assert.assertTrue(nextItem2.size == 1)
            Assert.assertTrue(nextItem2[0].symbol == "EURUSD")
        }
    }

    @Test
    fun test_getForexList_returnError() = runBlocking {
        coEvery { useCase.getForexList(listOf("EURUSD")) } returns
                flow { emit(Result.failure(Exception())) }
        viewModel.updateFxPair(listOf("EURUSD"))
        viewModel.fxListStateFlow.test {
            val nextItem = awaitItem()
            Assert.assertTrue(nextItem.isEmpty())
        }
        viewModel.isError.test{
            val nextItem = awaitItem()
            Assert.assertTrue(nextItem)
        }
    }
}