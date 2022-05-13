package com.example.base.domainlayer

import com.example.base.datalayer.models.WordsModel
import com.example.base.utils.ResultState
import java.net.URL

class UseCase (private val repository: Repository) {

    fun getResultFromHttp (url:String,result: (s: ResultState<ArrayList<WordsModel>>) -> Unit)
    =repository.getResultOfHttpRequest(url,result)

}