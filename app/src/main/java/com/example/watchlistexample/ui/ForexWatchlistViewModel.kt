package com.example.watchlistexample.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watchlistexample.data.model.AccountFxItem
import com.example.watchlistexample.domain.ForexDetail
import com.example.watchlistexample.domain.ForexWatchlistUseCase
import com.example.watchlistexample.ui.view.WatchlistItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class ForexWatchlistViewModel @Inject constructor(private val forexWatchlistUseCase: ForexWatchlistUseCase) :
    ViewModel() {

    private val _fxListStateFlow: MutableStateFlow<List<ForexDetail>> = MutableStateFlow(emptyList())
    val fxListStateFlow: StateFlow<List<WatchlistItem>> = _fxListStateFlow.map {
        it.map { forexDetail ->
            WatchlistItem(forexDetail.forexPair, forexDetail.prevPrice, forexDetail.sellPrice, forexDetail.buyPrice, forexDetail.currentPrice)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _fxPairFlow: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    private val _accountFxListStateFlow: MutableStateFlow<List<AccountFxItem>> = MutableStateFlow(emptyList())
    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)


    val equityFlow = _fxListStateFlow.filter { it.isNotEmpty() }.combine(_accountFxListStateFlow.filter { it.isNotEmpty() }) { fxList, accountFxList ->
        if (fxList.size != accountFxList.size || !fxList.map { it.forexPair }.containsAll(accountFxList.map { it.currencyPair })) {
            return@combine null
        }
        var balance = BigDecimal("0")
        accountFxList.forEach { accountFxItem ->
            val fx = fxList.find { accountFxItem.currencyPair == it.forexPair }
            if (fx != null) {
                val currentValue = fx.currentPrice.multiply(accountFxItem.amount).setScale(2, RoundingMode.CEILING)
                Log.e("ForexWatchlistViewModel", "currentValue: $currentValue")
                balance = balance.plus(currentValue)
            }
        }

        balance
    }.filterNotNull()
        .shareIn(viewModelScope, SharingStarted.Lazily, 0)

    val balanceFlow = _accountFxListStateFlow.filter { it.isNotEmpty() }.map {
        var balance = BigDecimal("0")
        it.forEach { accountFxItem ->
            balance = balance.plus(accountFxItem.balance)
        }
        balance
    }.shareIn(viewModelScope, SharingStarted.Lazily, 0)
    val marginFlow: StateFlow<BigDecimal> = MutableStateFlow(BigDecimal("12345"))
    val usedValueFlow: StateFlow<BigDecimal> = MutableStateFlow(BigDecimal("12345"))


    init {
        viewModelScope.launch {
            _fxPairFlow.filter { it.isNotEmpty() }.collectLatest {
                // reset fx list and account list when fx pair changes
                _fxListStateFlow.value = emptyList()
                _accountFxListStateFlow.value = emptyList()

                // start quoting the new fx pair
                forexWatchlistUseCase.getForexList(it).collectLatest {
                    _isLoading.value = false
                    it.getOrNull()?.let { fxDetailList ->
                        Log.e("ForexWatchlistViewModel", "fxDetailList: $fxDetailList")
                        _fxListStateFlow.value = fxDetailList
                        if (_accountFxListStateFlow.value.isEmpty()) {
                            _accountFxListStateFlow.value = forexWatchlistUseCase.getEquity(fxDetailList)?.getOrNull()
                                ?: emptyList()
                        }
                    }
                }
            }
        }
    }

    fun updateFxPair(pair: List<String>) {
        _isLoading.value = true
        _fxPairFlow.value = pair
    }
}