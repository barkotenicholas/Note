package com.barkote.mynotes.extendview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.core.content.ContextCompat
import com.barkote.mynotes.R

@SuppressLint("CustomViewStyleable")
class ColorSlider @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.seekBarStyle,
) : AppCompatSeekBar(
    context,
    attributeSet,
    defStyleAttr,
) {
    private var colors: ArrayList<Int> = arrayListOf(Color.RED, Color.YELLOW, Color.BLUE)
    val w = 48
    val h = 48
    val halfw = if (w >= 0) w / 2 else 1
    val halfh = if (h >= 0) h / 2 else 1
    private val paint = Paint()
    private var noColorDrawable:Drawable? = null
        set(value) {
             w2 = value?.intrinsicWidth ?: 0
             h2 = value?.intrinsicHeight ?: 0
             halfw2 = if (w2 >= 0) w / 2 else 1
             halfh2 = if (h2 >= 0) h / 2 else 1
            value?.setBounds(-halfw2, -halfh2, halfw2, halfh2)
            field = value
        }
    var w2 = 0
    private var h2 = 0
    private var halfw2 = 1
    private var halfh2 = 1

    init {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ColorSlider)
        try {
            colors = typedArray.getTextArray(R.styleable.ColorSlider_colors)
                .map {
                    Color.parseColor(it.toString())
                } as ArrayList<Int> /* = java.util.ArrayList<kotlin.Int> */
        } finally {
            typedArray.recycle()
        }
        colors.add(0, android.R.color.transparent)
        max = colors.size - 1
        progressBackgroundTintList =
            ContextCompat.getColorStateList(context, android.R.color.transparent)
        progressTintList = ContextCompat.getColorStateList(context, android.R.color.transparent)
        splitTrack = false
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom + 50)
        thumb = context.getDrawable(R.drawable.ic_baseline_arrow_drop_down_24)

        noColorDrawable = context.getDrawable(R.drawable.ic_baseline_clear_24)

        setOnSeekBarChangeListener(object : OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                listener.forEach{
                    it(colors[progress])
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    var selecetedColor:Int = android.R.color.transparent
        set(value) {
            var index = colors.indexOf(value)
            if(index == -1){
                progress = 0
            }else{
                progress = index
            }
        }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawTickMarks(canvas)

    }

    private var listener : ArrayList<(Int) -> Unit > = arrayListOf()

    fun addListener(function:(Int)-> Unit){
        listener.add(function)
    }

    private fun drawTickMarks(canvas: Canvas?) {

        canvas?.let {

            val count = colors.size
            val saveCount = canvas.save()
            canvas.translate(paddingLeft.toFloat(), (height / 2).toFloat() + 50f)
            if (count > 1) {

                val spacing = (width - paddingLeft - paddingRight) / (count - 1).toFloat()
                for (i in 0 until count) {


                    if (i == 0) {
                        noColorDrawable?.draw(canvas)
                    } else {
                         paint.color = colors[i]
                        canvas.drawRect(
                            -halfw.toFloat(),
                            -halfh.toFloat(),
                            halfw.toFloat(),
                            halfh.toFloat(),
                            paint
                        )
                    }

                    canvas.translate(spacing, 0f)
                }
                canvas.restoreToCount(saveCount)
            }


        }

    }
}
