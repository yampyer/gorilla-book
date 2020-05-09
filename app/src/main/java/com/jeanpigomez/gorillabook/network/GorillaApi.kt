package com.jeanpigomez.gorillabook.network

import com.jeanpigomez.gorillabook.model.Post
import io.reactivex.Observable
import retrofit2.http.GET

interface GorillaApi {

    @GET("feed")
    fun getFeed(): Observable<List<Post>>
}
