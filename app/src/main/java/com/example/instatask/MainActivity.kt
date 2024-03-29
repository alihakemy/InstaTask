package com.example.instatask

import android.os.Bundle
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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


        binding.initRecycler()

        binding.renderData()


    }


    private fun ActivityMainBinding.initRecycler() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@MainActivity)
        recyclerViewItems.layoutManager = layoutManager
        adapter = WorldListAdapter()
        recyclerViewItems.adapter = adapter

    }

    private fun ActivityMainBinding.renderData() {
        viewModel.resultData.observe(this@MainActivity, Observer {

            adapter.submitList(it)
            searchNotFound.isVisible = it.isNullOrEmpty()

            progressBar.isVisible = false
        })


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchWord(query)
                return false
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.searchWord(it) }
                return false
            }


        })
        sortToggle.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                viewModel.sortDescending()
            } else {
                viewModel.sortAscending()
            }
        }

        viewModel.stateResult.observe(this@MainActivity, Observer {
            if(it.equals("Error")){
                progressBar.isVisible = false
                error.isVisible = true
                searchNotFound.isVisible = false

            }
        })
        error.setOnClickListener {
            progressBar.isVisible = true
            error.isVisible = false
            viewModel.getWords()

        }


    }


    override fun onPause() {
        super.onPause()
    }


}
