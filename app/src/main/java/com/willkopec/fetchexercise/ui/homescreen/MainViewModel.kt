package com.willkopec.fetchexercise.ui.homescreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.willkopec.fetchexercise.model.FetchResponseItem
import com.willkopec.fetchexercise.repository.FetchRepository
import com.willkopec.fetchexercise.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val fetchRepository: FetchRepository,
) : ViewModel() {

    val TAG = "VIEWMODEL"

    private val _currentProductListData = MutableStateFlow<List<FetchResponseItem>>(emptyList())
    val currentProductListData = _currentProductListData.asStateFlow()

    private val _loadError = MutableStateFlow("")
    val loadError = _loadError.asStateFlow()

    init{
        Log.d(TAG, "View Model has been initialized here")

        viewModelScope.launch {
            getFetchProductList()
        }

    }

    fun getFetchProductList(){

        viewModelScope.launch {

            val result = fetchRepository.getFetchData()
            when(result){

                is Resource.Success -> {

                    val responseData = result.data?.map {

                        FetchResponseItem(
                            it.id,
                            it.listId,
                            it.name
                        )

                    } ?: emptyList()

                    _currentProductListData.value = responseData
                    sortDataByIdAndNameNumerically()
                    _loadError.value = ""

                }
                is Resource.Error -> {
                    _loadError.value = "Failed to get data from Fetch API or no internet connection"
                }

                else -> {}

            }

        }


    }

    fun sortDataByIdAndNameAlphabetically(){
        Log.d(TAG, "sorting HERE")
        _currentProductListData.value = _currentProductListData.value.sortedWith(compareBy(FetchResponseItem::listId, FetchResponseItem::name))
    }

    fun sortDataByIdAndNameNumerically(){
        Log.d(TAG, "sorting HERE")
        _currentProductListData.value = _currentProductListData.value.sortedWith(compareBy(FetchResponseItem::listId, FetchResponseItem::nameWithoutItem))
    }


}