package com.example.base

import android.os.*
import com.example.base.utils.convertToWordsModel
import com.example.base.utils.findWords
import com.example.base.datalayer.models.WordsModel
import com.example.base.utils.ResultState
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import kotlin.collections.ArrayList


class NetworkRequest(
    result: (s: ResultState<ArrayList<WordsModel>>) -> Unit
) {
    private var thread: HandlerThread = HandlerThread("HttpRequest")


    init {
        if (!thread.isAlive) {
            thread.start()
        }
    }

    private val handler = object : Handler(thread.looper) {
        override fun handleMessage(msg: Message) {


            if (msg.data.containsKey("KEY")) {
                try {
                    val url = URL(msg.data.getString("KEY")) //wrapping url object

                    val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
                    conn.readTimeout = 10000
                    conn.connectTimeout = 15000
                    conn.requestMethod = "GET"
                    conn.connect()


                    val result = readStream(conn.inputStream).toString().replace(" ","  ")
                    val wordsList = convertToWordsModel(result.findWords())

                    result(ResultState.Success(wordsList))
                    stopRequest()
                } catch (e: Exception) {
                    result(ResultState.Error(e.localizedMessage.toString()))
                    stopRequest()

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


    }

    fun stopRequest() {
        thread.quitSafely()
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