package com.lonard.camerlangproject.ui.homepage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lonard.camerlangproject.databinding.ActivityArticleDetailBinding
import com.lonard.camerlangproject.db.homepage.ArticleEntity
import com.lonard.camerlangproject.formatDateTime
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
                finishAfterTransition()
            }

            Picasso.get().load(articleParcel.thumbnailPic).into(articleDetailHeaderPic)

            articleDatetimePublishedInfo.text = articleParcel.createdAt?.formatDateTime()
            articleDurationReadApproxInfo.text = articleParcel.readDuration
            articleName.text = articleParcel.name

            Picasso.get().load(articleParcel.expertImage).into(expertPic)

            expertName.text = articleParcel.expertName
            expertSpecialization.text = articleParcel.expertSpecialization
            expertVerifiedDate.text = articleParcel.expertVerificationDate

            articleHeader.text = articleParcel.header
            articleContent.text = articleParcel.content
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
    }

    companion object {
        const val EXTRA_ARTICLE = "showed_article_information"
    }
}