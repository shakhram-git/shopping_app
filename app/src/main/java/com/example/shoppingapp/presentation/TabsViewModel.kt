package com.example.shoppingapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.shoppingapp.model.User
import com.example.domain.shoppingapp.usecase.GetCurrentUserInfoFlowUseCase
import com.example.domain.shoppingapp.usecase.GetIsCurrentUserExistFlowUseCase
import com.example.shoppingapp.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TabsViewModel @Inject constructor(
    getCurrentUserInfoFlowUseCase: GetCurrentUserInfoFlowUseCase
) : ViewModel() {

    private val _selectedTabItemId = Channel<Int>()
    val selectedTabItemId = _selectedTabItemId.receiveAsFlow()

    init {
        viewModelScope.launch {
            userInfo = getCurrentUserInfoFlowUseCase.execute()
                .stateIn(viewModelScope, SharingStarted.Eagerly, null)
        }
    }


    lateinit var userInfo: StateFlow<User?>


    fun goToProfileNavGraph(){
        viewModelScope.launch {
            _selectedTabItemId.send(R.id.profile_nav_graph)
        }
    }

    fun goToCartNavGraph(){
        viewModelScope.launch {
            _selectedTabItemId.send(R.id.cart_nav_graph)
        }
    }


}