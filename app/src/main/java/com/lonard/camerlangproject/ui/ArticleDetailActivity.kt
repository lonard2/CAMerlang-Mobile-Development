package com.lonard.camerlangproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lonard.camerlangproject.R
import com.lonard.camerlangproject.databinding.ActivityArticleDetailBinding
import com.lonard.camerlangproject.databinding.ActivityHomepageBinding
import com.squareup.picasso.Picasso

class ArticleDetailActivity : AppCompatActivity() {
    private lateinit var bind: ActivityArticleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.apply {
            Picasso.get().load(selectedArticleImgUrl).into(articleDetailHeaderPic)

            articleDatetimePublishedInfo.text = selectedArticleDatetime
            articleDurationReadApproxInfo.text = selectedArticleDurationApprox
            articleName.text = selectedArticleName

            articleHeader.text = selectedArticleHeader
            articleContent.text = selectedArticleContent
        }
    }

    companion object {
        const val EXTRA_ARTICLE = "showed_article_information"
    }
}