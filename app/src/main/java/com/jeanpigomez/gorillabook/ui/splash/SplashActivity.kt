package com.jeanpigomez.gorillabook.ui.splash

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.jeanpigomez.gorillabook.ui.feed.FeedActivity
import com.jeanpigomez.gorillabook.R

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 3000 // 1 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(this, FeedActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }
}
