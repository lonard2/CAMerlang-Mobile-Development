package com.lonard.camerlangproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lonard.camerlangproject.R
import com.lonard.camerlangproject.databinding.ActivityArticleDetailBinding
import com.lonard.camerlangproject.databinding.ActivityHomepageBinding
import com.lonard.camerlangproject.db.homepage.ArticleEntity
import com.lonard.camerlangproject.db.library.LibraryContentEntity
import com.lonard.camerlangproject.ui.rv_adapter.HomepageContentAdapter
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

            Picasso.get().load(articleParcel.selectedArticleImgUrl).into(articleDetailHeaderPic)

            articleDatetimePublishedInfo.text = articleParcel.selectedArticleDatetime
            articleDurationReadApproxInfo.text = articleParcel.selectedArticleDurationApprox
            articleName.text = articleParcel.selectedArticleName

            articleHeader.text = articleParcel.selectedArticleHeader
            articleContent.text = articleParcel.selectedArticleContent
        }
    }

    companion object {
        const val EXTRA_ARTICLE = "showed_article_information"
    }
}