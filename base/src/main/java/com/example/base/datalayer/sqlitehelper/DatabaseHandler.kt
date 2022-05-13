package com.example.base.datalayer.sqlitehelper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.annotation.WorkerThread
import com.example.base.datalayer.models.WordsModel

class DatabaseHandler(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query =
            ("CREATE TABLE " + TABLE_NAME + "( wordId  INTEGER PRIMARY KEY AUTOINCREMENT , " +
                    "word Text , repeatCount INTEGER )")

        // we are calling sqlite
        // method for executing our query
        db?.execSQL(query)
        db?.close()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("create table if not exists" + TABLE_NAME)
        onCreate(db)
    }

    @WorkerThread
    fun insertWord(wordsModel: WordsModel) {
        val values = ContentValues()
        values.put("word", wordsModel.word)
        values.put("repeatCount", wordsModel.wordRepeat)
        val db = writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }


    @SuppressLint("Range")
    @WorkerThread
    fun getResults(): ArrayList<WordsModel> {
        val listResult: ArrayList<WordsModel> = ArrayList()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        cursor.moveToFirst()
        while (cursor.moveToNext()) {

            listResult.add(
                WordsModel(
                    cursor.getString(cursor.getColumnIndex("word")),
                    cursor.getInt(cursor.getColumnIndex("repeatCount"))
                )
            )

        }

        // at last we close our cursor
        cursor.close()
        return listResult

    }


    companion object {
        // below is variable for database name
        private val DATABASE_NAME = "InstaBugWords"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        val TABLE_NAME = "words"

    }
}