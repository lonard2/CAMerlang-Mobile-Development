package com.lonard.camerlangproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lonard.camerlangproject.R
import com.lonard.camerlangproject.databinding.ActivityArticleDetailBinding
import com.lonard.camerlangproject.databinding.ActivityHomepageBinding

class ArticleDetailActivity : AppCompatActivity() {
    private lateinit var bind: ActivityArticleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)
    }
}