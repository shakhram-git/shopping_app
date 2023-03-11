package com.example.shoppingapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.shoppingapp.model.LoginError
import com.example.domain.shoppingapp.model.UserLoginData
import com.example.domain.shoppingapp.usecase.LoginByEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginByEmailUseCase: LoginByEmailUseCase): ViewModel() {

    private val _loginError = Channel<LoginError?>()
    val loginState = _loginError.receiveAsFlow()



    fun login(userLoginData: UserLoginData){
        viewModelScope.launch {
            _loginError.send(loginByEmailUseCase.execute(userLoginData))
        }
    }

}