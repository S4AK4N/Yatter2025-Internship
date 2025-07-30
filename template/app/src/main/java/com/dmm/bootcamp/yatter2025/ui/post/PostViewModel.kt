package com.dmm.bootcamp.yatter2025.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2025.common.navigation.Destination
import com.dmm.bootcamp.yatter2025.common.navigation.PopBackDestination
import com.dmm.bootcamp.yatter2025.domain.service.GetLoginUserService
import com.dmm.bootcamp.yatter2025.usecase.post.PostYweetUseCase
import com.dmm.bootcamp.yatter2025.usecase.post.PostYweetUseCaseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostViewModel(
    private val  postYweetUseCase: PostYweetUseCase,
    private val getLoginUserService: GetLoginUserService,
    ) :ViewModel(){

    private val _uiState: MutableStateFlow<PostUiState> = MutableStateFlow(PostUiState.empty())
    val uiState: MutableStateFlow<PostUiState> = _uiState

    private val _destination: MutableStateFlow<Destination?> = MutableStateFlow(null)
    val destination: MutableStateFlow<Destination?> = _destination


    // 画面の初期起動時にユーザー情報取得メソッド
    fun onCreate(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // ログイン済みユーザー取得
            val me = getLoginUserService.execute()

            // 必要アバター画像の情報のみ更新
            val snapshotBidingModel = uiState.value.bindingModel
            _uiState.update {
                it.copy(
                    bindingModel = snapshotBidingModel.copy(avaterUrl = me?.avatar?.toString()),
                    isLoading = false,
                )
            }
        }
    }

    //  Yweet内容書き換え時に呼び出されるメソッド
    fun onChangedYweet(yweetText: String) {

        // 入力された文字列にUiStateを更新
        _uiState.update { it.copy(bindingModel = uiState.value.bindingModel.copy(yweetText = yweetText)) }
    }

    //  投稿ボタン押下時に呼び出されるメソッド
    fun onClickPost(){

        // 投稿完了したら画面戻りエラーは何もしない
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = postYweetUseCase.execute(
                content = uiState.value.bindingModel.yweetText,
                attachmentList = listOf()
            )
            when(result){
                PostYweetUseCaseResult.Success ->{
                    _destination.value = PopBackDestination
                }
                is PostYweetUseCaseResult.Failure ->{
                    // エラー表示
                }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    // パブリックタイムラインに戻るために表示するボタン降下後のメソッド
    fun onClickNavIcon(){
        _destination.value = PopBackDestination
    }

    // 画面の終了後行き先を初期化するメソッド
    fun onCompletedNavigation(){
        _destination.value = null

    }

}