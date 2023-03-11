package com.example.shoppingapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.shoppingapp.usecase.GetIsCurrentUserExistFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getIsCurrentUserExistFlowUseCase: GetIsCurrentUserExistFlowUseCase
) : ViewModel() {
    val isUserExist:StateFlow<Boolean?> = getIsCurrentUserExistFlowUseCase.execute().stateIn(
        viewModelScope, SharingStarted.Eagerly, null
    )
}