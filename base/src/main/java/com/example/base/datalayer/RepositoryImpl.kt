package com.example.base.datalayer

import android.util.Log
import com.example.base.datalayer.models.WordsModel
import com.example.base.di.Providers
import com.example.base.domainlayer.Repository
import com.example.base.utils.ResultState
import org.w3c.dom.Document

class RepositoryImpl() : Repository {



    override fun getResultOfHttpRequest(url:String,result: (s: ResultState<ArrayList<WordsModel>>) -> Unit) {

        Providers
            .getNetworkRequest() {
                result(it)
            }
            .startGetHttpRequest(url)

    }

}