package com.dmm.bootcamp.yatter2025.ui.register

import com.dmm.bootcamp.yatter2025.ui.register.bindingmodel.RegisterUserBindingModel

data class RegisterUserUiState(
    val registerBindingModel: RegisterUserBindingModel,
    val isLoading: Boolean,
    val validUsername: Boolean,
    val validPassword: Boolean,

    ) {
    val isEnableRegister: Boolean = validUsername && validPassword

    companion object{
        fun empty(): RegisterUserUiState = RegisterUserUiState(
            registerBindingModel = RegisterUserBindingModel(
                username = "",
                password = ""
            ),
            isLoading = false,
            validUsername = false,
            validPassword = false,
        )
    }
}
