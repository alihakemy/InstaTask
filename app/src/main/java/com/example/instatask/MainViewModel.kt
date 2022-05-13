package com.example.instatask

import android.util.Log
import android.widget.Filter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.datalayer.models.WordsModel
import com.example.base.di.Providers
import com.example.base.utils.ResultState
import com.example.instatask.filter.WordsFilter
import kotlinx.coroutines.launch

class MainViewModel(private val state: SavedStateHandle) : ViewModel() {


    private val tempList: MutableLiveData<ArrayList<WordsModel>> = MutableLiveData()
    val resultData: MutableLiveData<ArrayList<WordsModel>> =
        MutableLiveData<ArrayList<WordsModel>>().also {
            getWords()
        }


    private fun getWords() {

        Providers.provideUseCase().getResultFromHttp(BuildConfig.BaseUrl) {

            when (it) {

                is ResultState.Success<ArrayList<WordsModel>> -> {

                    resultData.postValue(it.data)
                    tempList.postValue(it.data)
                    Log.e("Done", "Done")
                }

                else -> {
                    Log.e("Erroe", "Error")
                }
            }

        }
    }

    fun searchWord(query: String) {
        state["search"] = query
        state.getLiveData<String>("search").also { text ->
            viewModelScope.launch {
                WordsFilter.getInstance(resultData.value!!) {

                    if (text.value.toString().equals("")) {
                        resultData.postValue(tempList.value)
                    } else {
                        resultData.postValue(it)
                    }


                }.filter(text.value.toString())

            }
        }
    }


    fun sortDescending() {
        resultData.value?.sortByDescending {
            it.wordRepeat
        }
        resultData.postValue(resultData.value)

    }

    fun sortAscending () {
        resultData.value?.sortBy {
            it.wordRepeat
        }
        resultData.postValue(resultData.value)

    }

}
