package com.example.instatask

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instatask.adapter.WorldListAdapter
import com.example.instatask.databinding.ActivityMainBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: WorldListAdapter

    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading")
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        progressDialog.show()

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

            if (progressDialog.isShowing) {
                progressDialog.dismiss()
            }

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

        viewModel.errorResult.observe(this@MainActivity, Observer {

            if (progressDialog.isShowing) {
                progressDialog.dismiss()
            }
            error.isVisible = true
            searchNotFound.isVisible = false

        })


    }


    override fun onPause() {
        super.onPause()
    }


}
