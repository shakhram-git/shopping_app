package com.example.shoppingapp.presentation

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.shoppingapp.usecase.GetLatestProductsUseCase
import com.example.domain.shoppingapp.usecase.GetProductsNamesUseCase
import com.example.domain.shoppingapp.usecase.GetSaleProductsUseCase
import com.example.shoppingapp.presentation.mapper.toUi
import com.example.shoppingapp.presentation.model.Brand
import com.example.shoppingapp.presentation.model.CustomItemsList
import com.example.shoppingapp.presentation.model.ProductUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLatestProductsUseCase: GetLatestProductsUseCase,
    private val getSaleProductsUseCase: GetSaleProductsUseCase,
    private val getProductsNamesUseCase: GetProductsNamesUseCase
) : ViewModel() {

    private val handler = Handler(Looper.getMainLooper())
    private val token = Any()

    private val brands = List(5){Brand()}

    private val _networkData = MutableStateFlow<List<CustomItemsList>>(emptyList())
    val networkData = _networkData.asStateFlow()

    private val _productsName = MutableStateFlow<List<String>>(emptyList())
    val productsName = _productsName.asStateFlow()

    private val _loadingState = MutableStateFlow(State.LOADING)
    val loadingState = _loadingState.asStateFlow()

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    private val productsLoadExceptionHandler = CoroutineExceptionHandler { _, _ ->
        viewModelScope.launch {
            _loadingState.value = State.ERROR
            _error.send("Unable to connect to server")
        }
    }

    private val productsNamesLoadExceptionHandler = CoroutineExceptionHandler { _, _ ->
        viewModelScope.launch {
            _searchState.value = State.ERROR
            _error.send("Unable to connect to server")
        }
    }

    private val _searchState = MutableStateFlow(State.SUCCESS)
    val searchState = _searchState.asStateFlow()


    init {
        viewModelScope.launch(productsLoadExceptionHandler) {
            _loadingState.value = State.LOADING
            val latest = async {
                getLatestProductsUseCase.execute().map { it.toUi() }
            }
            val saleProducts = async {
                getSaleProductsUseCase.execute().map { it.toUi() }
            }
            val data = listOf(
                CustomItemsList("Latest", latest.await(), CustomItemsList.Type.SMALL_ICON),
                CustomItemsList("Flash Sale", saleProducts.await(), CustomItemsList.Type.BIG_ICON),
                CustomItemsList("Brands", brands, CustomItemsList.Type.SMALL_ICON)
            )
            _networkData.value = data
            _loadingState.value = State.SUCCESS
        }

    }

    fun getSearchHints(text: String){
        viewModelScope.launch {
            handler.removeCallbacksAndMessages(token)
            if (!text.isNullOrBlank()) {
                handler.postDelayed({
                    getProductsName()
                }, token, 1000L)
            }
        }
    }



    private fun getProductsName() {
        viewModelScope.launch(productsNamesLoadExceptionHandler) {
            _searchState.value = State.LOADING
            _productsName.value = getProductsNamesUseCase.execute()
            _searchState.value = State.SUCCESS
        }
    }

    enum class State {
        LOADING,
        ERROR,
        SUCCESS
    }
}