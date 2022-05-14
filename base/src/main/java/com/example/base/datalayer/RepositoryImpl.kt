package com.example.base.datalayer

import android.content.Context
import android.os.Looper
import android.util.Log
import com.example.base.datalayer.models.WordsModel
import com.example.base.datalayer.sqlitehelper.ManageDatabaseOperation
import com.example.base.di.Providers
import com.example.base.domainlayer.Repository
import com.example.base.utils.ResultState
import org.w3c.dom.Document
import kotlin.concurrent.thread

class RepositoryImpl() : Repository {


    override fun getResultOfHttpRequest(
        url: String,
        result: (s: ResultState<ArrayList<WordsModel>>) -> Unit
    ) {


        Providers
            .getNetworkRequest() {
                when (it) {
                    is ResultState.Success<ArrayList<WordsModel>> -> {

                            Providers.provideDatabaseOperation().storeItems(it.data)

                        result(it)
                    }
                    else -> {

                        result(ManageDatabaseOperation().getLocalData().get())

                    }
                }
            }
            .startGetHttpRequest(url)

    }

}