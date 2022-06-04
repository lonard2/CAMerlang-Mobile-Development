package com.lonard.camerlangproject.ui.consultation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lonard.camerlangproject.databinding.ActivityConsultationDetailBinding
import com.lonard.camerlangproject.db.consultation.ConsultationItemEntity
import com.lonard.camerlangproject.ui.images.ImageShowActivity
import com.squareup.picasso.Picasso

class ConsultationDetailActivity : AppCompatActivity() {
    private lateinit var bind: ActivityConsultationDetailBinding

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
                val viewZoomedImg = Intent(this@ConsultationDetailActivity, ImageShowActivity::class.java)
                viewZoomedImg.putExtra(ImageShowActivity.EXTRA_PIC, consultationParcel.analyzedImg)

                startActivity(viewZoomedImg)
            }
        }
    }

    companion object {
        const val EXTRA_CONSULTATION_DATA = "captured_information"
    }
}