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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading")
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        progressDialog.show()
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)



        binding.recyclerViewItems.layoutManager = layoutManager

        adapter = WorldListAdapter()
        binding.recyclerViewItems.adapter = adapter

        viewModel.resultData.observe(this, Observer {

            adapter.submitList(it)
            if (progressDialog.isShowing) {
                progressDialog.dismiss()
            }
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

        viewModel.errorResult.observe(this, Observer {

            if (progressDialog.isShowing) {
                progressDialog.dismiss()
            }
            binding.error.isVisible = true


        })




    }

    override fun onPause() {
        super.onPause()
    }


}
