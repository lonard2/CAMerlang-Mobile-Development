package com.lonard.camerlangproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lonard.camerlangproject.R
import com.lonard.camerlangproject.databinding.ActivityConsultationDetailBinding
import com.lonard.camerlangproject.databinding.ActivityCreditsBinding
import com.squareup.picasso.Picasso

class ConsultationDetailActivity : AppCompatActivity() {
    private lateinit var bind: ActivityConsultationDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityConsultationDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.apply {
            backBtn.setOnClickListener {
                finish()
            }
        }

        bind.apply {
            Picasso.get().load(analyzedImg).into(consultationTakenImage)

            consultationIdDetail.text = analyzedConsultationId
            consultationDatetimeDetail.text = analyzedConsultationDateTime
            consultationOutcomeDetail.text = analyzedConsultationOutcome

            firstProblemShow.text = retrievedTfResultFirstProblem
            secondProblemShow.text = retrievedTfResultSecondProblem
            thirdProblemShow.text = retrievedTfResultThirdProblem

            firstProblemPercentageTxt.text = retrievedTfResultFirstProblemPercentage
            secondProblemPercentageTxt.text = retrievedTfResultSecondProblemPercentage
            thirdProblemPercentageTxt.text = retrievedTfResultThirdProblemPercentage
        }
    }

    companion object {
        const val EXTRA_CONSULTATION_DATA = "captured_information"
    }
}