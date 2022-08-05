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

    private var scale = 1f
    private var tickSizeScale = tickSize*scale
    private var horizontalSize = 0f
    private var verticalSize = 0f

    private var centerHorizontal = 0f
    private var centerVertical = 0f

    private var scaleToFit = false

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
        refreshValues(true)

        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.ColorDialView)

        try {
            val customColors = typedArray.getTextArray(R.styleable.ColorDialView_colors)?.map {
                Color.parseColor(it.toString())

            }as ArrayList<Int>?

            customColors?.let {
                colors = customColors
            }

            dialDiameter = typedArray.getDimension(R.styleable.ColorDialView_dialDiameter,toDp(100).toFloat()).toInt()
            extraPadding = typedArray.getDimension(R.styleable.ColorDialView_tickPadding,toDp(30).toFloat()).toInt()
            tickSize = typedArray.getDimension(R.styleable.ColorDialView_tickRadius,toDp(10).toFloat())
            scaleToFit = typedArray.getBoolean(R.styleable.ColorDialView_scaleToFit,false)
        }finally {
            typedArray.recycle()
        }
    }

    private fun getCenteredBounds(dialDiameter: Int,scalar :Float = 1f): Rect {

        val half = ((if(dialDiameter>0)dialDiameter/2 else 1) *scalar).toInt()
        return Rect(-half,-half,half,half)

    }

    override fun onDraw(canvas: Canvas) {

        val saveCount = canvas.save()

        colors.forEachIndexed { index, _ ->

            if(index == 0) {
                canvas.translate(centerHorizontal,tickPositionVertical)
                noColorDrawable?.draw(canvas)
                canvas.translate(-centerHorizontal,-tickPositionVertical)
            }else{
                paint.color = colors[index]
                canvas.drawCircle(centerHorizontal,tickPositionVertical ,tickSizeScale,paint)
            }

            canvas.rotate(angleBetweenColors,centerHorizontal,centerVertical)
        }
        canvas.restoreToCount(saveCount)
        canvas.translate(centerHorizontal,centerVertical)
        dialDrawable?.draw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {


        if (scaleToFit) {
            refreshValues(false)
            val specWidth = MeasureSpec.getSize(widthMeasureSpec)
            val specHeight = MeasureSpec.getSize(heightMeasureSpec)
            val workingWidth = specWidth - paddingLeft - paddingRight
            val workingHeight = specHeight - paddingTop - paddingBottom
            scale = if (workingWidth < workingHeight) {
                (workingWidth) / (horizontalSize - paddingLeft - paddingRight)
            } else {
                (workingHeight) / (verticalSize - paddingTop - paddingBottom)
            }
            dialDrawable?.let {
                it.bounds = getCenteredBounds((dialDiameter * scale).toInt())
            }
            noColorDrawable?.let {
                it.bounds = getCenteredBounds((tickSize * scale).toInt(), 2f)
            }
            val width = resolveSizeAndState(
                (horizontalSize * scale).toInt(),
                widthMeasureSpec,
                0)
            val height = resolveSizeAndState(
                (verticalSize * scale).toInt(),
                heightMeasureSpec,
                0)
            refreshValues(true)
            setMeasuredDimension(width, height)
        } else {
            val width = resolveSizeAndState(horizontalSize.toInt(),
                widthMeasureSpec,
                0)
            val height = resolveSizeAndState(verticalSize.toInt(),
                heightMeasureSpec,
                0)
            setMeasuredDimension(width, height)
        }

    }
    private fun refreshValues(withScale:Boolean){

        val localScale = if (withScale) scale else 1f
        //Compute padding values
        this.totalLeftPadding = paddingLeft + extraPadding * localScale
        this.totalTopPadding = paddingTop + extraPadding * localScale
        this.totalRightPadding = paddingRight + extraPadding * localScale
        this.totalBottomPadding = paddingBottom + extraPadding * localScale

        //Compute helper values
        this.horizontalSize = paddingLeft + paddingRight + (extraPadding * localScale * 2) + dialDiameter * localScale
        this.verticalSize = paddingTop + paddingBottom + (extraPadding * localScale * 2) + dialDiameter * localScale

        //Compute position values
        this.tickPositionVertical = paddingTop + extraPadding * localScale / 2f
        this.centerHorizontal = totalLeftPadding + (horizontalSize - totalLeftPadding - totalRightPadding) / 2f
        this.centerVertical = totalTopPadding + (verticalSize - totalTopPadding - totalBottomPadding) / 2f
        this.tickSizeScale = tickSize * localScale
    }

    private fun toDp(value :Int ):Int{
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value.toFloat(),context.resources.displayMetrics).toInt()
    }
}