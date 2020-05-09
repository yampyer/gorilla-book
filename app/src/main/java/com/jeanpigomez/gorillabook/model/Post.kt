package com.jeanpigomez.gorillabook.model

import com.google.gson.annotations.SerializedName

data class Post (
        val id: Int,
        @SerializedName("first_name")
        val firstName: String,
        @SerializedName("last_name")
        val lastName: String,
        @SerializedName("post_body")
        val post: String,
        @SerializedName("unix_timestamp")
        val date: Long,
        val image: String?)
