package com.lonard.camerlangproject.ui.consultation

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.lonard.camerlangproject.databinding.ActivityConsultationDetailBinding
import com.lonard.camerlangproject.db.consultation.ConsultationItemEntity
import com.lonard.camerlangproject.ui.images.ImageShowActivity
import com.squareup.picasso.Picasso

class ConsultationDetailActivity : AppCompatActivity() {
    private lateinit var bind: ActivityConsultationDetailBinding

    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityConsultationDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val consultationParcel = intent.getParcelableExtra<ConsultationItemEntity>(
            EXTRA_CONSULTATION_DATA
        ) as ConsultationItemEntity

        bind.apply {

            backBtn.setOnClickListener {
                finish()
            }

            Picasso.get().load(consultationParcel.analyzedImg).into(consultationTakenImage)

            consultationIdDetail.text = consultationParcel.analyzedConsultationId
            consultationDatetimeDetail.text = consultationParcel.analyzedConsultationDateTime
            consultationOutcomeDetail.text = consultationParcel.analyzedConsultationOutcome

            consultationTakenImage.setOnClickListener {
                val sharedAnim =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        context as Activity,
                        Pair(bind.consultationTakenImage, "zoomed_image"),
                    )

                val viewZoomedImg = Intent(this@ConsultationDetailActivity, ImageShowActivity::class.java)
                viewZoomedImg.putExtra(ImageShowActivity.EXTRA_PIC, consultationParcel.analyzedImg)

                startActivity(viewZoomedImg, sharedAnim.toBundle())
            }
        }
    }

    companion object {
        const val EXTRA_CONSULTATION_DATA = "captured_information"
    }
}