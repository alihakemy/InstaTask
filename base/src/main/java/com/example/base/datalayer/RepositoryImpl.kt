package com.example.base.datalayer

import com.example.base.datalayer.models.WordsModel
import com.example.base.datalayer.sqlitehelper.ManageDatabaseOperation
import com.example.base.providers.Providers
import com.example.base.domainlayer.Repository
import com.example.base.utils.ResultState

class RepositoryImpl() : Repository {


    override fun getResultOfHttpRequest(
        url: String,
        result: (s: ResultState<ArrayList<WordsModel>>) -> Unit
    ) {


        Providers
            .getNetworkRequest() {
                when (it) {
                    is ResultState.Success<ArrayList<WordsModel>> -> {
                        result(it)


                        Providers.provideDatabaseOperation().storeItems(it.data)


                    }
                    else -> {

                        result(ManageDatabaseOperation().getLocalData().get())
                        ManageDatabaseOperation().closeExecutor()
                    }
                }
            }
            .startGetHttpRequest(url)

    }

}