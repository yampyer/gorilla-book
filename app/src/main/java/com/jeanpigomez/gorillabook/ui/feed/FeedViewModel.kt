package com.jeanpigomez.gorillabook.ui.feed

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.jeanpigomez.gorillabook.R
import com.jeanpigomez.gorillabook.base.BaseViewModel
import com.jeanpigomez.gorillabook.model.Post
import com.jeanpigomez.gorillabook.network.GorillaApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class FeedViewModel : BaseViewModel() {
    @Inject
    lateinit var gorillaApi: GorillaApi

    private var subscription: CompositeDisposable = CompositeDisposable()

    private val feedAdapter: FeedAdapter = FeedAdapter()
    private val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    private val errorMessage: MutableLiveData<Int> = MutableLiveData()
    private val feed: MutableLiveData<List<Post>> = MutableLiveData()

    init {
        loadingVisibility.value = View.GONE
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    fun getFeed() {
        subscription.add(gorillaApi.getFeed()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveFeedStart() }
                .doOnTerminate { onRetrieveFeedFinish() }
                .subscribe(this::onRetrieveFeedSuccess, this::onRetrieveFeedError))
    }

    private fun onRetrieveFeedStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveFeedFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveFeedSuccess(result: List<Post>) {
        if (result.isNotEmpty()) {
            feed.value = result
            feedAdapter.updateFeed(result)
        }
    }

    private fun onRetrieveFeedError(error: Throwable) {
        errorMessage.value = R.string.no_feed_available
    }

    fun getErrorMessage(): MutableLiveData<Int> = errorMessage

    fun getFeedData(): MutableLiveData<List<Post>> = feed

    fun getFeedAdapter(): FeedAdapter = feedAdapter

    fun getLoadingVisibility(): MutableLiveData<Int> = loadingVisibility

}
