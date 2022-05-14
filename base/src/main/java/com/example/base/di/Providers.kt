package com.example.base.di

import com.example.base.NetworkRequest
import com.example.base.datalayer.RepositoryImpl
import com.example.base.datalayer.models.WordsModel
import com.example.base.datalayer.sqlitehelper.DatabaseHelper
import com.example.base.datalayer.sqlitehelper.ManageDatabaseOperation
import com.example.base.domainlayer.UseCase
import com.example.base.utils.ResultState

object Providers {

    fun getNetworkRequest(result: (s: ResultState<ArrayList<WordsModel>>) -> Unit) = NetworkRequest(result)

    private fun provideRepository() = RepositoryImpl()

    fun provideUseCase() = UseCase(provideRepository())

    fun providesDatabaseHelper() = DatabaseHelper(null)

    fun provideDatabaseOperation()=ManageDatabaseOperation()


}