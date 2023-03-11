package com.example.shoppingapp.presentation.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.ItemProfileSettingsBinding


class CustomMenuItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0,
    defStyleRes: Int = 0
): FrameLayout(context, attrs, defStyleAttrs, defStyleRes) {

    private val binding: ItemProfileSettingsBinding

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.item_profile_settings, this, true)
        binding = ItemProfileSettingsBinding.bind(this)
        initializeAttrs(attrs, defStyleAttrs, defStyleRes)
    }

    private fun initializeAttrs(attrs: AttributeSet?, defStyleAttrs: Int, defStyleRes: Int){
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomMenuItemView, defStyleAttrs, defStyleRes)
        val nameText = typedArray.getString(R.styleable.CustomMenuItemView_nameText)
        binding.name.text = nameText
        val infoText = typedArray.getString(R.styleable.CustomMenuItemView_infoText)
        binding.infoText.text = infoText
        val actionVisibility = typedArray.getBoolean(R.styleable.CustomMenuItemView_actionVisibility, true)
        binding.goBtn.isVisible = actionVisibility
        val icon = typedArray.getDrawable(R.styleable.CustomMenuItemView_iconSrc)
        binding.icon.setImageDrawable(icon)
        typedArray.recycle()
    }
}