package com.example.base.datalayer.sqlitehelper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.annotation.WorkerThread
import com.example.base.MyApplication
import com.example.base.datalayer.models.WordsModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DatabaseHelper(factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(MyApplication.context, DATABASE_NAME, factory, DATABASE_VERSION) {
    private val db = this
    override fun onCreate(db: SQLiteDatabase?) {

        val query =
            ("CREATE TABLE " + TABLE_NAME + "( " +
                    "word Text PRIMARY KEY , repeatCount INTEGER )")


        db?.execSQL(query)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("create table if not exists" + TABLE_NAME)
        onCreate(db)
    }

    @WorkerThread
    fun insertWord(wordsModel: ArrayList<WordsModel>) {

        wordsModel.forEach {
            val values = ContentValues()
            values.put("word", it.word)
            values.put("repeatCount", it.wordRepeat)
            db.writableDatabase.insert(TABLE_NAME, null, values)


        }
        closeDatabase()

    }

    @WorkerThread
    fun checkEmpty(empty: (boolean: Boolean) -> Unit) {


        val cur: Cursor =  db.readableDatabase.rawQuery("SELECT COUNT(*) FROM $TABLE_NAME", null)
        cur?.let {
            if (!it.isClosed) {
                if (it.moveToFirst()) {
                    empty(it.getInt (0) == 0)

                }
            }

            it.close()
            closeDatabase()
        }


    }

    fun closeDatabase() {

        db.close()
    }

    @SuppressLint("Range")
    @WorkerThread
    fun getResults(): ArrayList<WordsModel> {
        val listResult: ArrayList<WordsModel> = ArrayList()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                listResult.add(
                    WordsModel(
                        cursor.getString(cursor.getColumnIndex("word")),
                        cursor.getInt(cursor.getColumnIndex("repeatCount"))
                    )
                )
            } while (cursor.moveToNext())
        }



        cursor.close()
        closeDatabase()
        return listResult

    }


    companion object {
        private val DATABASE_NAME = "InstaBugWords"

        private val DATABASE_VERSION = 1

        val TABLE_NAME = "words"

    }
}