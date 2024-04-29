package com.example.hiltexample

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hiltexample.data.model.User
import com.example.hiltexample.databinding.ActivityMainBinding
import com.example.hiltexample.ui.main.adapter.MainAdapter
import com.example.hiltexample.ui.main.adapter.MainViewModel
import com.example.hiltexample.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var adapter: MainAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var receiver: AirplaneModeChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupUI()
        setupObserver()

        receiver = AirplaneModeChangeReceiver()
        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(receiver,it)
        }
    }

    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        mainViewModel.users.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.isVisible = false
                    it.data?.let { users -> renderList(users) }
                    binding.recyclerView.isVisible = true
                }
                Status.LOADING -> {
                    binding.progressBar.isVisible = true
                    binding.recyclerView.isVisible = false
                }
                Status.ERROR -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(users: List<User>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }
}