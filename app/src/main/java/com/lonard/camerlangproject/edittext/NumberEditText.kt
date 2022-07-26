package com.lonard.camerlangproject.edittext

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.lonard.camerlangproject.R
import java.util.*

class NumberEditText: AppCompatEditText, View.OnTouchListener {

    private lateinit var clearBtn: Drawable

    private lateinit var edtStyle: Drawable

    private val locale: String = Locale.getDefault().language

    constructor(context: Context): super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if(compoundDrawables[2] != null) {
            val clearBtnStart: Float
            val clearBtnEnd: Float

            var isClearBtnClicked = false

            if(layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                clearBtnEnd = (clearBtn.intrinsicWidth + paddingStart).toFloat()
                if(event.x < clearBtnEnd) isClearBtnClicked = true
            } else {
                clearBtnStart = (width - paddingEnd - clearBtn.intrinsicWidth).toFloat()
                if(event.x > clearBtnStart) isClearBtnClicked = true
            }

            if(isClearBtnClicked) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        clearBtn = ContextCompat.getDrawable(context, R.drawable.clear_btn) as Drawable
                        showClearBtn()
                        return true
                    }

                    MotionEvent.ACTION_UP -> {
                        clearBtn = ContextCompat.getDrawable(context, R.drawable.clear_btn) as Drawable
                        if(text != null) text?.clear()

                        hideClearBtn()
                        return true
                    }
                    else -> return false
                }
            }
        }
        return false
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        background = edtStyle

        textSize = 14f
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {
        clearBtn = ContextCompat.getDrawable(context, R.drawable.clear_btn) as Drawable

        edtStyle = ContextCompat.getDrawable(context, R.drawable.rounded_box_edt) as Drawable

        setOnTouchListener(this)

        addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(seq: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(seq: CharSequence, start: Int, before: Int, after: Int) {
                if(seq.toString().isNotEmpty()) showClearBtn() else hideClearBtn()
            }

            override fun afterTextChanged(seq: Editable) {
                if(seq.isEmpty()) {
                    error = when(locale) {
                        "in" -> {
                            "Kolom ini kosong. Ayo isi dengan informasi yang sesuai!"
                        }
                        "en" -> {
                            "This column is empty. Let's fill with relevant information!"
                        }
                        else -> {
                            "COLUMN EMPTY!"
                        }
                    }
                }
            }
        })
    }

    private fun showClearBtn() {
        putInsideDrawables(endBox = clearBtn)
    }

    private fun hideClearBtn() {
        putInsideDrawables(endBox = null)
    }

    private fun putInsideDrawables (
        startBox: Drawable? = null,
        topBox: Drawable? = null,
        endBox: Drawable? = null,
        bottomBox: Drawable? = null,
    ) {
        setCompoundDrawablesRelativeWithIntrinsicBounds(
            startBox,
            topBox,
            endBox,
            bottomBox
        )
    }

}