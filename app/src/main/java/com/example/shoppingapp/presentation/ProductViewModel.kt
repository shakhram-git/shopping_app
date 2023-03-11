package com.example.shoppingapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.shoppingapp.model.ProductInfo
import com.example.domain.shoppingapp.usecase.AddProductToFavouriteUseCase
import com.example.domain.shoppingapp.usecase.GetProductInfoUseCase
import com.example.domain.shoppingapp.usecase.GetUserFavouritesFlowUseCase
import com.example.domain.shoppingapp.usecase.RemoveProductFromFavouriteUseCase
import com.example.shoppingapp.presentation.model.AmountWithTotalPrice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductInfoUseCase: GetProductInfoUseCase,
    private val addProductToFavouriteUseCase: AddProductToFavouriteUseCase,
    private val removeProductFromFavouriteUseCase: RemoveProductFromFavouriteUseCase,
    private val getUserFavouritesFlowUseCase: GetUserFavouritesFlowUseCase
) : ViewModel() {

    private val _loadingState = MutableStateFlow(State.LOADING)
    val loadingState = _loadingState.asStateFlow()

    private val loadingExceptionHandler = CoroutineExceptionHandler { _, _ ->
        viewModelScope.launch {
            _loadingState.value = State.ERROR
        }
    }

    private val _productInfo = MutableStateFlow<ProductInfo?>(null)
    val productInfo = _productInfo.asStateFlow()

    init {
        getProductInfo()
    }

    private lateinit var favourites: StateFlow<List<String>>


    val isInFavourite = combine(favourites, _productInfo) { favList, product ->
        product?.name in favList
    }.stateIn(viewModelScope, SharingStarted.Eagerly, false)


    private val _itemsAmount = MutableStateFlow(MIN_AMOUNT)
    val amountWithTotalPrice = combine(_productInfo, _itemsAmount) { product, amount ->
        return@combine AmountWithTotalPrice(amount,
            product?.let { amount * product.price } ?: 0.0)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, AmountWithTotalPrice(MIN_AMOUNT, 0.0))


    private fun getProductInfo() {
        viewModelScope.launch(loadingExceptionHandler) {
            val productInfo = launch {
                _loadingState.value = State.LOADING
                _productInfo.value = getProductInfoUseCase.execute()
                _loadingState.value = State.SUCCESS
            }
            val favourites = launch {
                favourites = getUserFavouritesFlowUseCase.execute()
                    .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
            }
            listOf(productInfo, favourites).joinAll()
        }
    }

    fun removeOneItem() {
        viewModelScope.launch {
            if (_itemsAmount.value > 1)
                _itemsAmount.value--
        }
    }

    fun addOneItem() {
        viewModelScope.launch {
            _itemsAmount.value++
        }
    }

    fun removeFromFavourite() {
        viewModelScope.launch {
            val productName = _productInfo.value?.name
            productName?.let { removeProductFromFavouriteUseCase.execute(it) }
        }
    }

    fun addToFavourite() {
        viewModelScope.launch {
            val productName = _productInfo.value?.name
            productName?.let { addProductToFavouriteUseCase.execute(it) }
        }
    }

    enum class State {
        LOADING,
        ERROR,
        SUCCESS
    }

    companion object {
        const val MIN_AMOUNT = 1
    }

}