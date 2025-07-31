package com.dmm.bootcamp.yatter2025.ui.timeline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2025.common.navigation.Destination
import com.dmm.bootcamp.yatter2025.domain.repository.YweetRepository
import com.dmm.bootcamp.yatter2025.ui.post.PostDestination
import com.dmm.bootcamp.yatter2025.ui.timeline.bindingmodel.converter.YweetConverter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PublicTimelineViewModel(private val yweetRepository: YweetRepository,): ViewModel() {

    // backing propertiesの定義
    private  val _uiState: MutableStateFlow<PublicTimelineUiState> =
        MutableStateFlow(PublicTimelineUiState.empty())
    val uiState: StateFlow<PublicTimelineUiState> = _uiState.asStateFlow()

    // FABが押された時にイベント処理、遷移の遷移情報
    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()


//
    private  suspend fun fetchPublicTimeline(){
        val yweetList = yweetRepository.findAllPublic()
        _uiState.update{
            it.copy(
                yweetList = YweetConverter.convertToBindingModel(yweetList),
            )

        }

    }



    fun onResume(){
        viewModelScope.launch{
            _uiState.update{it.copy(isLoading = true)} // ローディング開始
            fetchPublicTimeline()   // データフェッチ
            _uiState.update{it.copy(isLoading = false)} // ローディング終了
        }

    }

    fun onRefresh(){
        viewModelScope.launch{
            _uiState.update{it.copy(isRefreshing = true)} // リフレッシュ開始
            fetchPublicTimeline()   // データフェッチ
            _uiState.update{it.copy(isRefreshing = false)} // リフレッシュ終了
        }
    }

    // ポストボタンが押された時の遷移
    fun onClickPost(){
        _destination.value = PostDestination()
    }

    // 遷移完了後
    fun onCompleteNavigation() {
        _destination.value = null
    }


}