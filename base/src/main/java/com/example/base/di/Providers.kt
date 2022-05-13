package com.example.base.di

import android.content.Context
import com.example.base.NetworkRequest
import com.example.base.datalayer.RepositoryImpl
import com.example.base.datalayer.models.WordsModel
import com.example.base.datalayer.sqlitehelper.DatabaseHandler
import com.example.base.domainlayer.UseCase
import com.example.base.utils.ResultState
import java.util.logging.Handler

object Providers {

    fun getNetworkRequest(result: (s: ResultState<ArrayList<WordsModel>>) -> Unit) = NetworkRequest(result)

    private fun provideRepository() = RepositoryImpl()

    fun provideUseCase() = UseCase(provideRepository())

    fun providesDatabaseHelper() = DatabaseHandler(null)



}