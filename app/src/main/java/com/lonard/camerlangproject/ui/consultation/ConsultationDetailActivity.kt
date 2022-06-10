package com.lonard.camerlangproject.ui.consultation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.lonard.camerlangproject.R
import com.lonard.camerlangproject.databinding.ActivityConsultationDetailBinding
import com.lonard.camerlangproject.db.consultation.ConsultationItemEntity
import com.lonard.camerlangproject.ml.ObjectDetectionUtil
import com.lonard.camerlangproject.mvvm.ConsultationViewModel
import com.lonard.camerlangproject.mvvm.ConsultationViewModelFactory
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

            Picasso.get().load(consultationParcel.consultationImg).into(consultationTakenImage)

            consultationIdDetail.text = getString(R.string.consultation_id_format, consultationParcel.id.toString())
            consultationDatetimeDetail.text = consultationParcel.processedAt

            val consultationTakenImageOnUri = Uri.parse(consultationParcel.consultationImg)

            val detectionResult = ObjectDetectionUtil.objectDetection(consultationTakenImageOnUri)

            runOnUiThread {
                consultationTakenImage.setImageBitmap(detectionResult)
            }

            consultationTakenImage.setOnClickListener {
                val sharedAnim =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        context as Activity,
                        Pair(bind.consultationTakenImage, "zoomed_image"),
                    )

                val viewZoomedImg = Intent(this@ConsultationDetailActivity, ImageShowActivity::class.java)
                viewZoomedImg.putExtra(ImageShowActivity.EXTRA_PIC, consultationParcel.consultationImg)

                startActivity(viewZoomedImg, sharedAnim.toBundle())
            }

            val consultFactory: ConsultationViewModelFactory = ConsultationViewModelFactory.getFactory(this@ConsultationDetailActivity)
            val consultViewModel: ConsultationViewModel by viewModels {
                consultFactory
            }

//            consultViewModel.retrieveConsultationDetections().observe(this@ConsultationDetailActivity) { detections ->
//                showDetectionResults(detections)
//            }
        }
    }

//    private fun showDetectionResults(detectionItems: List<ConsultationDetectionItemEntity>) {
//        bind.consultationOutcomeCard.layoutManager = LinearLayoutManager(this@ConsultationDetailActivity,
//            LinearLayoutManager.VERTICAL, false)
//
//        val consultHistoryAdapter = ConsultationDetailAdapter(detectionItems as ArrayList<ConsultationDetectionItemEntity>)
//        bind.consultationOutcomeCard.adapter = consultHistoryAdapter
//    }

    companion object {
        const val EXTRA_CONSULTATION_DATA = "captured_consultation_information"
    }
}