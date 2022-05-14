package com.example.instatask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.base.datalayer.models.WordsModel
import com.example.base.providers.Providers
import com.example.base.utils.ResultState
import com.example.instatask.filter.WordsFilter

class MainViewModel(private val state: SavedStateHandle) : ViewModel() {

    private val useCase = Providers.provideUseCase()
    val errorResult: MutableLiveData<String> = MutableLiveData()

    private val tempList: MutableLiveData<ArrayList<WordsModel>> = MutableLiveData()
    val resultData: MutableLiveData<ArrayList<WordsModel>> =
        MutableLiveData<ArrayList<WordsModel>>().also {
            getWords()
        }


    private fun getWords() {

        useCase.getResultFromHttp(BuildConfig.BaseUrl) {

            when (it) {

                is ResultState.Success<ArrayList<WordsModel>> -> {

                    resultData.postValue(it.data)
                    tempList.postValue(it.data)
                }

                else -> {
                    errorResult.postValue("Error")
                }
            }

        }
    }

    fun searchWord(query: String) {
        state["search"] = query
        state.getLiveData<String>("search").also { text ->
            resultData.value?.let {
                WordsFilter.getInstance(it) {

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

    fun sortAscending() {
        resultData.value?.sortBy {
            it.wordRepeat
        }
        resultData.postValue(resultData.value)

    }

}
