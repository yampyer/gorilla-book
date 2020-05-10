package com.jeanpigomez.gorillabook.ui.createpost

import android.Manifest
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.View
import com.jeanpigomez.gorillabook.R
import com.jeanpigomez.gorillabook.databinding.ActivityCreatePostBinding
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.support.annotation.StringRes
import android.widget.Toast
import com.bumptech.glide.Glide
import com.jeanpigomez.gorillabook.model.Post
import com.jeanpigomez.gorillabook.ui.feed.FeedActivity.Companion.EXTRA_POST
import java.util.*

class CreatePostActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CreatePostActivity::class.java)
        }

        const val IMAGE_PICK_CODE = 1000
        const val PERMISSION_CODE = 1001
    }

    private lateinit var binding: ActivityCreatePostBinding
    private lateinit var viewModel: CreatePostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_post)
        viewModel = ViewModelProviders.of(this).get(CreatePostViewModel::class.java)
        binding.viewModel = viewModel

        initViews()
    }

    private fun initViews() {
        (binding.toolbar as ConstraintLayout).apply {
            getViewById(R.id.appIcon).visibility = View.GONE
            getViewById(R.id.back_icon).visibility = View.VISIBLE
            getViewById(R.id.back_icon).setOnClickListener {
                finish()
            }
            getViewById(R.id.toolbar_title).visibility = View.VISIBLE
            getViewById(R.id.toolbar_right_cta).visibility = View.VISIBLE
            getViewById(R.id.toolbar_right_cta).setOnClickListener {
                if (binding.etPost.text.toString() == "") showError(R.string.please_enter_post_content)
                else {
                    val newPost = Post(0, "Jane", "Doe", binding.etPost.text.toString(), Date().time, viewModel.picture.value)
                    setResult(Activity.RESULT_OK, Intent().putExtra(EXTRA_POST, newPost))
                    finish()
                }
            }
        }

        binding.btnAddPhoto.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                } else
                    pickImageFromGallery()
            } else
                pickImageFromGallery()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    pickImageFromGallery()
                else
                    showError(R.string.permission_denied)
            }
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK)
            when (requestCode) {
                IMAGE_PICK_CODE -> {
                    viewModel.picture.value = data?.data?.toString()
                    binding.ivPicture.visibility = View.VISIBLE
                    Glide.with(this).load(data?.data).into(binding.ivPicture)
                }
            }
    }

    private fun showError(@StringRes errorMessage: Int) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }
}
