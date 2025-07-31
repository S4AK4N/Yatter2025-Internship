package com.dmm.bootcamp.yatter2025.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2025.common.navigation.Destination
import com.dmm.bootcamp.yatter2025.domain.model.Password
import com.dmm.bootcamp.yatter2025.usecase.register.RegisterUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.dmm.bootcamp.yatter2025.domain.model.Username
import com.dmm.bootcamp.yatter2025.usecase.register.RegisterUserUseCaseResult
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUserUseCase: RegisterUserUseCase
) :ViewModel(){
    private val _uiState: MutableStateFlow<RegisterUiState> = MutableStateFlow(RegisterUiState.empty())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()


    // 導線時に使用
    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()

    fun onChangedUsername(username: String) {
        val snapshotBindingModel = uiState.value.registerBindingModel
        _uiState.update{
            it.copy(
                validUsername = Username(username).validate(),
                registerBindingModel = snapshotBindingModel.copy(
                    username = username
                )
            )
        }
    }

    fun onChangedPassword(password: String) {
        val snapshotBindingModel = uiState.value.registerBindingModel

        _uiState.update {
            it.copy(
                validPassword = Password(password).validate(),
                registerBindingModel = snapshotBindingModel.copy(
                    password = password
                )
            )
        }
    }

    fun onClickRegister() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) } // 1

            val snapBindingModel = uiState.value.registerBindingModel
            when (
                val result =
                    registerUserUseCase.execute(
                        Username(snapBindingModel.username).value,
                        Password(snapBindingModel.password).value
                    ) // 2
            ) {
                is RegisterUserUseCaseResult.Success -> {
                    // 3
                    // パブリックタイムライン画面に遷移する処理の追加
                }

                is RegisterUserUseCaseResult.Failure -> {
                    // 4
                    // エラー表示
                }
            }

            _uiState.update { it.copy(isLoading = false) } // 5
        }
    }

    fun onClickLogin() {
        // _destination.value = RegisterUserDestination()
    }
    fun onCompleteNavigation() {}

}