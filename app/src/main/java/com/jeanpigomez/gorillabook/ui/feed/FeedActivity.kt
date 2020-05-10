package com.jeanpigomez.gorillabook.ui.feed

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.jeanpigomez.gorillabook.R
import com.jeanpigomez.gorillabook.databinding.ActivityFeedBinding
import com.jeanpigomez.gorillabook.model.Post
import com.jeanpigomez.gorillabook.ui.createpost.CreatePostActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FeedActivity : AppCompatActivity() {

    companion object {
        const val NEW_POST = 200
        const val EXTRA_POST = "extra_post"
    }

    private lateinit var binding: ActivityFeedBinding
    private lateinit var viewModel: FeedViewModel
    private lateinit var posts: ArrayList<Post>

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
        val simpleDateFormat = SimpleDateFormat(getString(R.string.today_date_format), Locale.getDefault())
        binding.tvTodayDate.text = simpleDateFormat.format(Date())
        binding.rvFeed.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.tvWhatOnYourMind.setOnClickListener {
            startActivityForResult(CreatePostActivity.newIntent(this), NEW_POST)
        }
    }

    private fun initObservers() {
        viewModel.getErrorMessage().observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage)
        })

        viewModel.getFeedData().observe(this, Observer {
            it?.let {
                posts = ArrayList(it)
            }
        })
    }

    private fun showError(@StringRes errorMessage: Int) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK)
            when (requestCode) {
                NEW_POST -> {
                    data?.getParcelableExtra<Post>(EXTRA_POST)?.let { post ->
                        posts.add(0, post)
                        viewModel.getFeedAdapter().updateFeed(posts)
                    }
                }
            }
    }
}
