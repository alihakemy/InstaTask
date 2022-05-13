package com.example.instatask

import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Filter
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.base.datalayer.models.WordsModel
import com.example.instatask.adapter.WorldListAdapter
import com.example.instatask.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: WorldListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)



        binding.recyclerViewItems.layoutManager = layoutManager

        adapter = WorldListAdapter()
        binding.recyclerViewItems.adapter = adapter

        viewModel.resultData.observe(this, Observer {
            adapter.submitList(it)
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchWord(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.searchWord(it) }

                return false
            }


        })
        binding.sortToggle.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                viewModel.sortDescending()
            } else {

                viewModel.sortAscending()
            }
        }


//        Thread {
//            Providers.providesDatabaseHelper(this).insertWord(WordsModel("G", 1))
//            Providers.providesDatabaseHelper(this).getResults()
//            Log.e(
//                "Result", Providers.providesDatabaseHelper(this)
//                    .getResults().toString()
//            )
//        }.start()


    }

    override fun onPause() {
        super.onPause()
    }


}
