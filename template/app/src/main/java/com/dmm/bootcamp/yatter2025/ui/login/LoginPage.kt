package com.dmm.bootcamp.yatter2025.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.Navigation.findNavController
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginPage(
    loginViewModel: LoginViewModel = getViewModel(),
){
    val uiState: LoginUiState by loginViewModel.uiState.collectAsState()
    val destination by loginViewModel.destination.collectAsState()
    val navController = LocalNavController.current

    LaunchedEffect(destination)  {
        destination?.let {
            it.navigate(navController)
            loginViewModel.onCompleteNavigation()
        }
    }
    LoginTemplate(
        userName = uiState.loginBindingModel.username,
        onChangeUserName = loginViewModel::onChangedUsername,
        password = uiState.loginBindingModel.password,
        onChangePassword = loginViewModel::onChangedPassword,
        isEnableLogin = uiState.isEnableLogin,
        isLoading = uiState.isLoading,
        onClickLogin = loginViewModel::onClickLogin,
        onClickRegister = loginViewModel::onClickRegister,
        )
}