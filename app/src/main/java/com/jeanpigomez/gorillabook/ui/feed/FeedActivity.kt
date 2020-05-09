package com.jeanpigomez.gorillabook.ui.feed

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.jeanpigomez.gorillabook.R
import com.jeanpigomez.gorillabook.databinding.ActivityFeedBinding

class FeedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeedBinding
    private lateinit var viewModel: FeedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_feed)
        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        binding.viewModel = viewModel

        initViews()
        initObservers()

        viewModel.getFeed()
    }

    private fun initViews() {
        binding.rvFeed.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.tvWhatOnYourMind.setOnClickListener {
            // Open create post activity
        }
    }

    private fun initObservers() {
        viewModel.getErrorMessage().observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage)
        })
    }

    private fun showError(@StringRes errorMessage: Int) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }
}
