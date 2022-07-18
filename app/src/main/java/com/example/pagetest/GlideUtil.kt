package com.example.pagetest

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.webp.decoder.WebpDrawable
import com.bumptech.glide.integration.webp.decoder.WebpDrawableTransformation
import com.bumptech.glide.load.resource.bitmap.CenterInside

class GlideUtil {
    companion object {
        fun loadWebp(
            imageView: ImageView,
            url: String?
        ) {
            val centerInside = CenterInside()
            Glide.with(imageView.context)
                .load(url)
                .optionalTransform(centerInside)
                .optionalTransform(
                    WebpDrawable::class.java,
                    WebpDrawableTransformation(centerInside)
                )
                .into(imageView)
        }
    }
}