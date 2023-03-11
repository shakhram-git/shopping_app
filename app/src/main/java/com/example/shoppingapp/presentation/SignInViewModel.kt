package com.example.shoppingapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.shoppingapp.model.NewUser
import com.example.domain.shoppingapp.usecase.CreateNewUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val createNewUserUseCase: CreateNewUserUseCase) :
    ViewModel() {

    private val _isSignInSuccessful = Channel<Boolean>()
    val isSignInSuccessful = _isSignInSuccessful.receiveAsFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        viewModelScope.launch {
            _isSignInSuccessful.send(false)
        }
    }

    fun createNewUser(newUser: NewUser) {
        viewModelScope.launch(exceptionHandler) {
            createNewUserUseCase.execute(newUser)
            _isSignInSuccessful.send(true)
        }
    }

}