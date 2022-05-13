package com.example.base.datalayer

import android.util.Log
import androidx.annotation.WorkerThread
import com.example.base.datalayer.models.WordsModel
import com.example.base.di.Providers
import java.util.regex.Matcher
import java.util.regex.Pattern

@WorkerThread
fun String.findWords(): HashMap<String, Int> {
    val map: HashMap<String, Int> = HashMap()
    val p: Pattern = Pattern.compile("\\s(\\w+)\\s")
    val m: Matcher = p.matcher(this)
    while (m.find()) {
        if (map.containsKey(m.group(m.groupCount()).toString())) {
            map[m.group(m.groupCount()).toString()]?.plus(m.groupCount())?.toInt()?.let {
                map[m.group(m.groupCount())] = it
            }

        } else {
            map[m.group(m.groupCount()).toString()] = 1
        }
    }

    return map

}


@WorkerThread
fun convertToWordsModelAndInsertToDatabase(map: HashMap<String, Int>): ArrayList<WordsModel> {

    val list: ArrayList<WordsModel> = ArrayList()
    for (key in map.keys) {
        list.add(WordsModel(key, map[key]?.toInt() ?: 0))


    }



    return list

}
