package com.dmm.bootcamp.yatter2025.ui.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.koin.androidx.compose.getViewModel

@Composable
fun RegisterPage(
    ResgisterViewModel: RegisterUserViewModel = getViewModel(),
){
    val uiState = ResgisterViewModel.uiState.collectAsState()

    RegisterTemplate(
        userName = uiState.value.registerBindingModel.username,
        onChangeUserName = ResgisterViewModel::onChangedUsername,
        password = uiState.value.registerBindingModel.password,
        onChangePassword = ResgisterViewModel::onChangedPassword,
        isEnableRegister = uiState.value.isEnableRegister,
        isLoading = uiState.value.isLoading,
        onClickRegister = ResgisterViewModel::onClickRegister,
        onClickLogin = ResgisterViewModel::onClickLogin,
    )
}