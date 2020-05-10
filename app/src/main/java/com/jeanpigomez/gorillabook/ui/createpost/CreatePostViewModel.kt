package com.jeanpigomez.gorillabook.ui.createpost

import android.arch.lifecycle.MutableLiveData
import com.jeanpigomez.gorillabook.base.BaseViewModel
import com.jeanpigomez.gorillabook.model.Post

class CreatePostViewModel : BaseViewModel() {
    var post = MutableLiveData<Post>()
    var picture = MutableLiveData<String>()
}
