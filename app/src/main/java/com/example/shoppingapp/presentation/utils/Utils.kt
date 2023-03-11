package com.example.shoppingapp.presentation.utils

import android.content.Context
import android.util.DisplayMetrics
import java.text.NumberFormat
import java.util.*

object Utils {

    fun Context.dpToPixel(dp: Int): Int {
        return (dp * (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }

    fun formatToCurrency(price: Double): String {
        return NumberFormat.getCurrencyInstance(Locale("en", "US")).format(price)
    }
}