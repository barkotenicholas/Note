package com.barkote.mynotes

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

class ColorDialView @JvmOverloads constructor(context: Context,
                                              attrs: AttributeSet?= null,
                                              defStyleAttr  :Int =0,
                                              defStyleRes: Int) : View(context, attrs, defStyleAttr, defStyleRes) {

    private var colors:ArrayList<Int> = arrayListOf(Color.RED,Color.YELLOW,Color.BLUE,Color.GREEN,Color.DKGRAY,Color.CYAN,Color.MAGENTA,Color.BLACK)

    var dialDrawable : Drawable? = null
    var noColorDrawable : Drawable? = null
    private var dialDiameter  = toDp(100)
    private var extraPadding  = toDp(30)
    private var tickSize = toDp(10).toFloat()
    private var angleBetweenColors = 0f

    private var totalLeftPadding = 0f
    private var totalTopPadding = 0f
    private var totalRightPadding = 0f
    private var totalBottomPadding = 0f

    private var tickPositionVertical = 0f

    private var horizontalSize = 0f
    private var verticalSize = 0f

    private var centerHorizontal = 0f
    private var centerVertical = 0f

    private var paint = Paint().also {
        it.color = Color.BLUE
        it.isAntiAlias = true
    }

    init {

        dialDrawable = context.getDrawable(R.drawable.ic_dail).also {
            it?.bounds = getCenteredBounds(dialDiameter)
            it?.setTint(Color.DKGRAY)
        }
        noColorDrawable = context.getDrawable(R.drawable.ic_baseline_clear_24).also {
            it?.bounds = getCenteredBounds(tickSize.toInt(),2f)
        }
        colors.add(0,Color.TRANSPARENT)
        angleBetweenColors = 360f / colors.size
        refreshValues()

    }

    private fun getCenteredBounds(dialDiameter: Int,scalar :Float = 1f): Rect {

        val half = ((if(dialDiameter>0)dialDiameter/2 else 1) *scalar).toInt()
        return Rect(-half,-half,half,half)

    }

    override fun onDraw(canvas: Canvas) {

        val saveCount = canvas.save()

        colors.forEachIndexed { index, i ->

            if(index == 0) {
                canvas.translate(centerHorizontal,tickPositionVertical)
                noColorDrawable?.draw(canvas)
                canvas.translate(-centerHorizontal,-tickPositionVertical)
            }else{
                paint.color = colors[index]
                canvas.drawCircle(centerHorizontal,tickPositionVertical ,tickSize,paint)
            }

            canvas.rotate(angleBetweenColors,centerHorizontal,centerVertical)
        }
        canvas.restoreToCount(saveCount)
        canvas.translate(centerHorizontal,centerVertical)
        dialDrawable?.draw(canvas)
    }

    private fun refreshValues(){

        this.totalLeftPadding = (paddingLeft+extraPadding).toFloat()
        this.totalRightPadding = (paddingRight+extraPadding).toFloat()
        this.totalBottomPadding = (paddingBottom+extraPadding).toFloat()
        this.totalTopPadding = (paddingTop+extraPadding).toFloat()

        this.tickPositionVertical = paddingTop + extraPadding / 2f
        this.centerHorizontal = totalLeftPadding +( horizontalSize -  totalLeftPadding - totalRightPadding )/ 2f
        this.centerVertical = totalTopPadding +(verticalSize - totalTopPadding - totalBottomPadding/ 2f)

        this.horizontalSize = paddingLeft + paddingRight + (extraPadding * 2) + dialDiameter.toFloat()
        this.verticalSize = paddingTop+ paddingBottom + (extraPadding *2) +dialDiameter.toFloat()

        this.centerVertical = verticalSize /2f
        this.centerHorizontal = horizontalSize /2f
    }

    private fun toDp(value :Int ):Int{
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value.toFloat(),context.resources.displayMetrics).toInt()
    }
}