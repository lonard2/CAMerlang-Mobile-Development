package com.lonard.camerlangproject.ui.homepage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lonard.camerlangproject.databinding.ActivityArticleDetailBinding
import com.lonard.camerlangproject.db.homepage.ArticleEntity
import com.squareup.picasso.Picasso

class ArticleDetailActivity : AppCompatActivity() {
    private lateinit var bind: ActivityArticleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val articleParcel = intent.getParcelableExtra<ArticleEntity>(EXTRA_ARTICLE) as ArticleEntity

        bind.apply {
            backBtn.setOnClickListener {
                finish()
            }

            Picasso.get().load(articleParcel.thumbnailPic).into(articleDetailHeaderPic)

            articleDatetimePublishedInfo.text = articleParcel.createdAt
            articleDurationReadApproxInfo.text = articleParcel.readDuration
            articleName.text = articleParcel.name

            articleHeader.text = articleParcel.header
            articleContent.text = articleParcel.content
        }
    }

    companion object {
        const val EXTRA_ARTICLE = "showed_article_information"
    }
}