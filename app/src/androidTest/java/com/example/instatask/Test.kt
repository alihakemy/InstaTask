package com.example.instatask

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.base.datalayer.models.WordsModel
import com.example.base.providers.Providers
import com.example.base.utils.findWords

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class Test {
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
        Providers.provideDatabaseOperation().storeItems(list)
        Providers.provideDatabaseOperation().closeExecutor()
    }
    @Test
    fun checkEmpty() {
        Providers.providesDatabaseHelper().checkEmpty {
            assertEquals(it,true)
        }
    }

    @Test
    fun regex() {


        assertEquals( "All modern operating 8 9 systems support & concurrency both via processes and threads".replace(" ","  ").findWords().size,11)

    }



}