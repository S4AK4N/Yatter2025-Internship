package com.dmm.bootcamp.yatter2025.ui.post

import com.dmm.bootcamp.yatter2025.ui.post.bindingmodel.PostBindingModel

data class PostUiState(
    val bindingModel: PostBindingModel,
    val isLoading: Boolean,
){
    companion object {
        fun empty(): PostUiState = PostUiState(
            bindingModel = PostBindingModel(
                avaterUrl = null,
                yweetText = "",
            ),
            isLoading = false,
        )
    }
}
