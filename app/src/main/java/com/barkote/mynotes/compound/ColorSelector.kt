package com.barkote.mynotes.compound

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.barkote.mynotes.R
import com.barkote.mynotes.databinding.ColorSelectorBinding


class ColorSelector @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attributes, defStyleAttr, defStyleRes) {

    private var listOfColors = listOf(Color.BLUE,Color.RED,Color.GREEN)
    private var selectedColorIndex = 0
    private  var binding:ColorSelectorBinding
    init {
        orientation = HORIZONTAL
        binding = ColorSelectorBinding.inflate(LayoutInflater.from(context),this)

        val typedArray = context.obtainStyledAttributes(attributes, R.styleable.ColorSlider)

        listOfColors = typedArray.getTextArray(R.styleable.ColorSlider_colors).map {
            Color.parseColor(it.toString())
        }

        typedArray.recycle()

        binding.selectorColor.setBackgroundColor(listOfColors[selectedColorIndex])


        binding.leftArrow.setOnClickListener{
            selectPreviousColor()
        }

        binding.rightArrow.setOnClickListener {
            selectNextColor()
        }

        binding.ColorEnabled.setOnCheckedChangeListener { _, _ ->
            broadCastColor()
        }
    }

    private fun selectPreviousColor() {
        if(selectedColorIndex == 0){
            selectedColorIndex = listOfColors.lastIndex
        }else{
            selectedColorIndex --
        }
        binding.selectorColor.setBackgroundColor(listOfColors[selectedColorIndex])
        broadCastColor()
    }

    private fun selectNextColor() {
        if(selectedColorIndex == listOfColors.lastIndex){
            selectedColorIndex = 0
        }else{
            selectedColorIndex++
        }
        binding.selectorColor.setBackgroundColor(listOfColors[selectedColorIndex])
        broadCastColor()
    }

    private var onColorSelectListener: ArrayList<(Int) -> Unit> = arrayListOf()

    fun addListener(color : (Int) -> Unit){
        this.onColorSelectListener.add(color)
    }


    var selectedColorValue : Int = android.R.color.transparent
        set(value) {
            var index = listOfColors.indexOf(value)
            if(index == -1){
                index = 0
                binding.ColorEnabled.isChecked = false
            }else{
                binding.ColorEnabled.isChecked = true
            }
            selectedColorIndex = index
            binding.selectorColor.setBackgroundColor(listOfColors[selectedColorIndex])
        }



    private fun broadCastColor(){
        val color = if (binding.ColorEnabled.isChecked){
            listOfColors[selectedColorIndex]}
        else{
            Color.TRANSPARENT
        }
        this.onColorSelectListener.forEach{function ->
            function(color)
        }
    }


}