package com.jeanpigomez.gorillabook.di.component

import com.jeanpigomez.gorillabook.di.module.NetworkModule
import com.jeanpigomez.gorillabook.ui.feed.FeedViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Component providing inject() methods for viewModels.
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    fun inject(feedViewModel: FeedViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}
