package com.jeanpigomez.gorillabook.ui.feed

import android.arch.lifecycle.MutableLiveData
import com.jeanpigomez.gorillabook.base.BaseViewModel
import com.jeanpigomez.gorillabook.model.Post
import java.text.SimpleDateFormat
import java.util.*

class PostViewModel : BaseViewModel() {
    private val name = MutableLiveData<String>()
    private val date = MutableLiveData<String>()
    private val image = MutableLiveData<String>()
    private val postBody = MutableLiveData<String>()

    fun bind(post: Post) {
        name.value = "${post.firstName} ${post.lastName}"
        val simpleDateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        date.value = simpleDateFormat.format(Date(post.date))
        image.value = post.image ?: ""
        postBody.value = post.post
    }

    fun getName(): MutableLiveData<String> = name

    fun getPost(): MutableLiveData<String> = postBody

    fun getImage(): MutableLiveData<String> = image

    fun getDate(): MutableLiveData<String> = date
}
