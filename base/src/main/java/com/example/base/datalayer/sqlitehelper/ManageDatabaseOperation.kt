package com.example.base.datalayer.sqlitehelper

import com.example.base.datalayer.models.WordsModel
import com.example.base.di.Providers
import com.example.base.utils.ResultState
import java.lang.Exception
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class ManageDatabaseOperation {
    var executor: ExecutorService = Executors.newFixedThreadPool(1)


    fun getLocalData(): Future<ResultState<ArrayList<WordsModel>>> {
        val task: Callable<ResultState<ArrayList<WordsModel>>> =
            Callable<ResultState<ArrayList<WordsModel>>> {
                val list = Providers.providesDatabaseHelper().getResults()

                if (!list.isNullOrEmpty()) {
                    ResultState.Success(list)
                } else {
                    ResultState.Error("Empty")
                }


            }
        return executor.submit<ResultState<ArrayList<WordsModel>>>(task)
    }

    fun storeItems(wordsModel: ArrayList<WordsModel>) {
        executor.execute {
            Providers.providesDatabaseHelper().checkEmpty {
                if (it) {
                    Providers.providesDatabaseHelper().insertWord(wordsModel)

                }

            }
        }

    }

    fun closeExecutor() {
        executor.shutdown()
    }

}