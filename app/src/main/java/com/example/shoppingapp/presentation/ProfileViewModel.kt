package com.example.shoppingapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.shoppingapp.model.User
import com.example.domain.shoppingapp.usecase.GetCurrentUserInfoFlowUseCase
import com.example.domain.shoppingapp.usecase.RemoveCurrentUserUseCase
import com.example.domain.shoppingapp.usecase.UpdateUserPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val removeCurrentUserInfoUseCase: RemoveCurrentUserUseCase,
    private val updateUserPhotoUseCase: UpdateUserPhotoUseCase
) : ViewModel() {

    fun logOut() {
        viewModelScope.launch {
            removeCurrentUserInfoUseCase.execute()
        }
    }

    fun updateUserPhoto(photoUri: String, userId: Long?) {
        viewModelScope.launch {
            userId?.let {
                updateUserPhotoUseCase.execute(it, photoUri)
            }
        }
    }
}