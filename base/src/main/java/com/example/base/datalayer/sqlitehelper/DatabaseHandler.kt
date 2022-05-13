package com.example.base.datalayer.sqlitehelper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.annotation.WorkerThread
import com.example.base.MyApplication
import com.example.base.datalayer.models.WordsModel
import java.lang.Exception

class DatabaseHandler(factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(MyApplication.context, DATABASE_NAME, factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query =
            ("CREATE TABLE " + TABLE_NAME + "( " +
                    "word Text , repeatCount INTEGER )")

        // we are calling sqlite
        // method for executing our query
        db?.execSQL(query)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("create table if not exists" + TABLE_NAME)
        onCreate(db)
    }

    @WorkerThread
    fun insertWord(wordsModel: ArrayList<WordsModel>) {
        val db = this.writableDatabase

        wordsModel.forEach {
            val values = ContentValues()
            values.put("word", it.word)
            values.put("repeatCount", it.wordRepeat)
            db.insert(TABLE_NAME, null, values)


        }

        db.close()
    }

    @WorkerThread
    fun checkEmpty(empty: (boolean: Boolean) -> Unit) {

        val db = this.readableDatabase
        val cur: Cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_NAME", null);
        if (cur != null && cur.moveToFirst()) {
            empty(cur.getInt(0) == 0)
        }

        cur.close();

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


        // at last we close our cursor
        cursor.close()
        db.close()
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