package com.example.pagetest

import android.animation.Animator
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.pagetest.Constants.USER_LOTTIE_ANIMATION
import com.example.pagetest.Constants.USER_PAGE_ANIMATION
import com.example.pagetest.databinding.FlashActiviyBinding
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import org.libpag.PAGFile
import org.libpag.PAGScaleMode
import org.libpag.PAGView

class FlashActivity : AppCompatActivity()  {
    private lateinit var binding: FlashActiviyBinding
    private var lottieView: LottieAnimationView? = null
    var pagView: PAGView? = null
    private var imageView: SimpleDraweeView? = null

    private val lottieFile = "flash_anim/data.json"
    private val lottieImages = "flash_anim/images"

    val animationType: Int = Constants.GLOBEL_ANIMTIONTYPE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FlashActiviyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (animationType == USER_PAGE_ANIMATION) {
           PAGView(this).let {
                pagView = it
                it.layoutParams =
                RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
               it.setScaleMode(PAGScaleMode.Zoom)
                binding.root.addView(it)
            }
            playPageSource()
        } else if (animationType == USER_LOTTIE_ANIMATION) {
            //lottie
            lottieView = LottieAnimationView(this).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
                imageAssetsFolder = lottieImages
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
            binding.root.addView(lottieView)
            showLottieSource()
        } else {
            val imageUri : String = "res://" + this.baseContext.getPackageName()+"/" + R.mipmap.ic_ic
            val controller = Fresco.newDraweeControllerBuilder()
                .setUri(imageUri)
                .setAutoPlayAnimations(true)
                .build()
            imageView = SimpleDraweeView(this).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                this.controller = controller
                binding.root.addView(this)
            }
        }
    }

    private fun playPageSource() {
        pagView?.let {
            val pagFile1 = PAGFile.Load(assets, "flash.pag")
            it.composition = pagFile1
            it.setRepeatCount(1)
            it.play()
            it.addListener(object : PAGView.PAGViewListener  {
                override fun onAnimationStart(view: PAGView?) {

                }

                override fun onAnimationEnd(view: PAGView?) {
                    jumpToMain()
                }

                override fun onAnimationCancel(view: PAGView?) {

                }

                override fun onAnimationRepeat(view: PAGView?) {

                }

            })
        }
    }

    private fun showLottieSource() {
        window.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        lottieView?.apply {
            setAnimation(lottieFile)
            imageAssetsFolder = lottieImages
            addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    jumpToMain()
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }
            })
        }

        lottieView?.playAnimation()
    }

    fun jumpToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}