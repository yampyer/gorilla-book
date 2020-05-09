package com.jeanpigomez.gorillabook.base

import android.arch.lifecycle.ViewModel
import com.jeanpigomez.gorillabook.di.component.ViewModelInjector
import com.jeanpigomez.gorillabook.di.module.NetworkModule
import com.jeanpigomez.gorillabook.di.component.DaggerViewModelInjector
import com.jeanpigomez.gorillabook.ui.feed.FeedViewModel

abstract class BaseViewModel : ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
            .builder()
            .networkModule(NetworkModule)
            .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is FeedViewModel -> injector.inject(this)
        }
    }
}
