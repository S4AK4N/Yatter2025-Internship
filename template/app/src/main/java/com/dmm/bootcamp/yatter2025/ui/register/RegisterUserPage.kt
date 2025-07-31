package com.dmm.bootcamp.yatter2025.ui.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.dmm.bootcamp.yatter2025.ui.LocalNavController
import org.koin.androidx.compose.getViewModel

@Composable
fun RegisterUserPage(
    registerViewModel: RegisterUserViewModel = getViewModel(),
){
    val uiState = registerViewModel.uiState.collectAsState()

    val destination = registerViewModel.destination.collectAsState()
    val navController = LocalNavController.current
    LaunchedEffect(destination.value) {
        destination.value?.let {
            it.navigate(navController)
            registerViewModel.onCompleteNavigation()
        }
    }
    RegisterTemplate(
        userName = uiState.value.registerBindingModel.username,
        onChangeUserName = registerViewModel::onChangedUsername,
        password = uiState.value.registerBindingModel.password,
        onChangePassword = registerViewModel::onChangedPassword,
        isEnableRegister = uiState.value.isEnableRegister,
        isLoading = uiState.value.isLoading,
        onClickRegister = registerViewModel::onClickRegister,
        onClickLogin = registerViewModel::onClickLogin,
    )
}