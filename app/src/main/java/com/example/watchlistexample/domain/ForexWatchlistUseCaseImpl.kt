package com.example.watchlistexample.domain

import com.example.watchlistexample.data.interfaces.AccountBalanceRepo
import com.example.watchlistexample.data.interfaces.ForexListRepo
import com.example.watchlistexample.domain.model.AccountFxItem
import com.example.watchlistexample.domain.interfaces.ForexWatchlistUseCase
import com.example.watchlistexample.domain.model.ForexDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ForexWatchlistUseCaseImpl @Inject constructor(
    private val forexListRepo: ForexListRepo,
    private val accountBalanceRepo: AccountBalanceRepo) : ForexWatchlistUseCase {


    override suspend fun getForexList(pairs: List<String>): Flow<Result<List<ForexDetail>>> {
        return forexListRepo.getForexList(pairs)
    }

    override suspend fun getEquity(fxList: List<ForexDetail>): Result<List<AccountFxItem>> {
        return try {
            Result.success(accountBalanceRepo.getAccountFxList(fxList))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun resumeRealTimeUpdate() {
        forexListRepo.resumeRealTimeUpdate()
    }

    override fun stopRealTimeUpdate() {
        forexListRepo.stopRealTimeUpdate()
    }
}