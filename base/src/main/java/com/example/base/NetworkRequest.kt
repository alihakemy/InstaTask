package com.example.base

import android.os.*
import android.text.Html
import android.util.Log
import androidx.core.text.HtmlCompat
import com.example.base.datalayer.convertToWordsModel
import com.example.base.datalayer.findWords
import com.example.base.datalayer.models.WordsModel
import com.example.base.utils.ResultState
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class NetworkRequest(result: (s: ResultState<ArrayList<WordsModel>>) -> Unit) {
    private var thread: HandlerThread = HandlerThread("HttpRequest")


    init {
        if (!thread.isAlive) {
            thread.start()
        }
    }

    private val handler = object : Handler(thread.looper) {
        override fun handleMessage(msg: Message) {

            if (msg.data.containsKey("KEY")) {
                kotlin.runCatching {
                    val url = URL(msg.data.getString("KEY")) //wrapping url object

                    val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
                    conn.readTimeout = 10000
                    conn.connectTimeout = 15000
                    conn.requestMethod = "GET"
                    conn.connect()
                    conn.inputStream

                }.onSuccess {
                    val result = readStream(it).toString()
                    result(ResultState.Success(result.findWords().convertToWordsModel()))
                }.onFailure {
                    result(ResultState.Error(it.localizedMessage.toString()))
                }
            }


        }
    }

    fun startGetHttpRequest(url: String) {


        val msg = Message()
        val b = Bundle()
        b.putString("KEY", url)
        msg.data = b
        handler.sendMessage(msg)

        for (i in 0..4) {
            Log.d("TAG", "sending " + i + " in " + Thread.currentThread())
            handler.sendEmptyMessageDelayed(i, 0)
        }


    }

    fun stopRequest() {
        thread?.quitSafely()
    }


    private fun readStream(stream: InputStream): String? {
        val buffer = ByteArray(1024)
        val byteArray = ByteArrayOutputStream()
        var out: BufferedOutputStream? = null

        var length = 0
        out = BufferedOutputStream(byteArray)
        while (stream.read(buffer).also { length = it } > 0) {
            out.write(buffer, 0, length)
        }

        out.flush()
        out.close()

        return byteArray.toString()


    }

}