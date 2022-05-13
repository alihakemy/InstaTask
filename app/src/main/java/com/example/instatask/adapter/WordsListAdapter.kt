package com.example.instatask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.base.datalayer.models.WordsModel
import com.example.instatask.databinding.ListItemBinding
import com.example.instatask.filter.WordsFilter

class WorldListAdapter() :
    RecyclerView.Adapter<WordsViewHolder>() {
    private val list: ArrayList<WordsModel> = ArrayList()
    fun submitList(list: ArrayList<WordsModel>?) {

        if (list != null) {
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {


        return WordsViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        holder.bind(list[position])

    }

    override fun getItemCount() = list.size



}

class WordsViewHolder(private val binding: ListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: WordsModel) {
        binding.name.text = model.word
        binding.countTextView.text = model.wordRepeat.toString()

    }

}
