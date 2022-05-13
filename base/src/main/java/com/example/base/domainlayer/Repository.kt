package com.example.base.domainlayer

import com.example.base.datalayer.models.WordsModel
import com.example.base.utils.ResultState

interface Repository {

    fun getResultOfHttpRequest (url:String,result: (s: ResultState<ArrayList<WordsModel>>) -> Unit)
}