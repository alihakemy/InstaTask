package com.example.instatask

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.base.datalayer.models.WordsModel
import com.example.base.di.Providers
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import kotlin.concurrent.thread

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.instatask", appContext.packageName)
    }

    @Test
    fun testInsertItemInDataBase() {

        val list: ArrayList<WordsModel> = ArrayList()

        list.add(WordsModel("a", 1))
        Providers.providesDatabaseHelper().insertWord(list)
    }

    @After
    fun closeDb() {
        Providers.providesDatabaseHelper().closeDatabase()
    }

}