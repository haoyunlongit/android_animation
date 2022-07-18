package com.example.pagetest

import android.content.Context

class UiUtils {
    companion object {
        /**
         * 获取屏幕高度
         */
        fun getScreenHeight(context: Context): Int {
            return context.resources.displayMetrics.heightPixels
        }

        /**
         * 获取屏幕宽度
         */
        fun getScreenWidth(context: Context): Int {
            return context.resources.displayMetrics.widthPixels
        }

        fun getScreenRatio(context: Context): Float {
            return getScreenHeight(context).toFloat() / getScreenWidth(context)
        }
    }
}