package com.phone.base.common.utils

import android.graphics.Color
import android.graphics.PorterDuff
import android.widget.ImageView
import com.phone.base.common.LIGHT_LEVEL_MAX
import java.lang.Exception
import kotlin.math.min

/**
 * description ：
 * author :
 * email : @waclighting.com.cn
 * date : 2020/7/30
 */

private const val COMPENSATION_VALUE = 20

object ImageUtils {

    @Suppress("DEPRECATION")
    fun setImageColor(image: ImageView, isOn: Boolean, level: Int) {
        if (isOn) {
            setImageColor(image, level)
        } else {
            image.background?.apply {
                setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN)
            }
        }
    }

    @Suppress("DEPRECATION")
    fun setImageColor(image: ImageView, level: Int) {
        try {
            LogUtil.d("setImageColor level=$level")
            image.background?.apply {
                var percent = ((255 - COMPENSATION_VALUE) / LIGHT_LEVEL_MAX.toFloat()) * min(level, LIGHT_LEVEL_MAX)
                percent += COMPENSATION_VALUE
                //"#ffffbb33"
                if (percent < 16) {
                    val color = "#0${Integer.toHexString(percent.toInt())}ffbb33"
                    LogUtil.d("color= $color")
                    setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_IN)
                } else {
                    val color = "#${Integer.toHexString(percent.toInt())}ffbb33"
                    LogUtil.d("color= $color")
                    setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_IN)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}