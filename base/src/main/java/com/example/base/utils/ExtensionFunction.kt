package com.example.base.datalayer

import android.util.Log
import com.example.base.datalayer.models.WordsModel
import java.util.regex.Matcher
import java.util.regex.Pattern


fun String.findWords(): HashMap<String, Int> {
    val map: HashMap<String, Int> = HashMap()


    Log.e("TextHEre", this.toString())

    val regex: Pattern = Pattern.compile("[\\^[0-9]\\+{}$&+,:;=\\\\?@#|/'<>.^*()%!-]")


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
        Log.e("IDDD", m.group(m.groupCount()).toString())
    }



    Log.e("ListMAP", map.toString())


    return map

}


fun HashMap<String,Int>.convertToWordsModel():ArrayList<WordsModel>{

    val list: ArrayList<WordsModel>  = ArrayList()

    for (key in keys) {
        list.add(WordsModel(key,get(key)?.toInt()?:0))


    }

    return list

}
