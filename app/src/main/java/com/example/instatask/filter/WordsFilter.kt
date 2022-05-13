package com.example.instatask.filter

import android.widget.Filter
import com.example.base.datalayer.models.WordsModel

class WordsFilter(
    private val list: ArrayList<WordsModel>,
    private val result: (ArrayList<WordsModel>) -> Unit
) : Filter() {
    override fun performFiltering(constraint: CharSequence): FilterResults {
        val filteredList: ArrayList<WordsModel> = ArrayList()
        if (constraint == null || constraint.isEmpty()) {
            filteredList.addAll(list)
        } else {
            val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
            for (item in list) {
                if (item.word.toLowerCase().contains(filterPattern)) {
                    filteredList.add(item)
                }
            }
        }
        val results = FilterResults()
        results.values = filteredList


        return results
    }

    override fun publishResults(constraint: CharSequence, results: FilterResults) {
        val newList = results.values as ArrayList<WordsModel>

        result(newList)

    }

    companion object {

        fun getInstance(
            list: ArrayList<WordsModel>,
            result: (ArrayList<WordsModel>) -> Unit
        ): WordsFilter {

            return WordsFilter(list, result)
        }

    }
}