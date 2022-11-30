package com.example.watchlistexample.data.datasource

import com.example.watchlistexample.domain.ForexDetail
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*


class AccountBalanceRepoImplTest{

    val sut = AccountBalanceRepoImpl()

    @Test
    fun testGetAccountFxList() = runBlocking{
        val fxList = listOf(ForexDetail("EURUSD", BigDecimal("1.03721"), 1.2.toBigDecimal(), 1.0.toBigDecimal(), 1.1.toBigDecimal(), Date().time))
        val result = sut.getAccountFxList(fxList)
        assert(result.size == 1)
    }

    @Test
    fun testStartingBalance() = runBlocking{
        val fxList = listOf(ForexDetail("EURUSD", BigDecimal("1.03721"), 1.2.toBigDecimal(), 1.0.toBigDecimal(), 1.1.toBigDecimal(), Date().time))
        val result = sut.getAccountFxList(fxList)
        result.forEachIndexed { index, accountFxItem ->
            Assert.assertEquals((accountFxItem.amount * fxList[index].currentPrice).setScale(2,RoundingMode.DOWN), BigDecimal("10000").setScale(2))
        }
    }

}