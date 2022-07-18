package com.example.pagetest

import android.annotation.SuppressLint
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.example.pagetest.databinding.FragmentThirdBinding
import org.libpag.PAGComposition
import org.libpag.PAGFile
import org.libpag.PAGScaleMode
import org.libpag.PAGView

class ThirdFragment: Fragment() {

    private var _binding: FragmentThirdBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var contentView: LinearLayout? = null

    val animationType: Int = Constants.GLOBEL_ANIMTIONTYPE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        contentView = binding.contentView

        if (animationType == Constants.USER_PAGE_ANIMATION) {
            addPageView(10, 100)
        } else if (animationType == Constants.USER_WEBP_ANIMATION) {
            for (i in 0..2) {
                addLineView(4, Constants.USER_WEBP_ANIMATION)
            }
        } else {
            for (i in 0..2) {
                addLineView(4, Constants.USER_LOTTIE_ANIMATION)
            }
        }

        return binding.root
    }

    private fun addPageView(row: Int, colume: Int) {
        val pagView = PAGView(context)
        var width = 0
        var height = 0
        context?.let {
            width = UiUtils.getScreenWidth(it)
            height = UiUtils.getScreenHeight(it)
        }
        var itemWidth = (width / row).toInt()
        pagView.let {
            it.layoutParams =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    itemWidth * colume
                )
            it.setScaleMode(PAGScaleMode.Zoom)
            contentView?.addView(it)
        }

        var pagComposition = PAGComposition.Make(width, itemWidth * colume)
        for (i in 0..row * colume) {
            pagComposition.addLayer(getPAGPagFile(i / row, i % row, "home_bd0.pag", itemWidth.toFloat(), itemWidth.toFloat()))
        }
        pagView.setComposition(pagComposition)
        pagView.setRepeatCount(0)
        pagView.play()
    }

    private fun getPAGPagFile(
        row: Int,
        colume: Int,
        name: String,
        itemWidth: Float,
        itemHeight: Float
    ): PAGFile? {
        val pagFile = PAGFile.Load(context?.assets, name)
        val matrix = Matrix()
        val scaleX = itemWidth * 1.0f / pagFile.width()
        matrix.preScale(scaleX, scaleX)
        matrix.postTranslate(itemWidth * colume, row * itemHeight)
        pagFile.setMatrix(matrix)
        pagFile.setDuration(10000000)
        return pagFile
    }

    private fun playPageSource(pagView: PAGView,  position: Int) {
        val index = position % 10
        val fileName = "home_bd" + index + ".pag"
        pagView.let {
            val pagFile1 = PAGFile.Load(pagView.context.assets, fileName)
            it.composition = pagFile1
            it.setRepeatCount(0)
            it.play()
            it.addListener(object : PAGView.PAGViewListener  {
                override fun onAnimationStart(view: PAGView?) {

                }

                override fun onAnimationEnd(view: PAGView?) {

                }

                override fun onAnimationCancel(view: PAGView?) {

                }

                override fun onAnimationRepeat(view: PAGView?) {

                }

            })
        }
    }


    private fun addLineView(num: Int, animationType: Int) {
        var width = 0
        var height = 0
        context?.let {
            width = UiUtils.getScreenWidth(it)
            height = UiUtils.getScreenHeight(it)
        }
        LinearLayout(context).let { linearlayout ->
            linearlayout.layoutParams =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    width / num
                )
            linearlayout.orientation = LinearLayout.HORIZONTAL
            for (i in 1..num) {
                if (animationType == Constants.USER_WEBP_ANIMATION) {
                    ImageView(context).let {
                        it.layoutParams =
                            RelativeLayout.LayoutParams(
                                width/ num,
                                width/ num
                            )
                        it.scaleType = ImageView.ScaleType.CENTER_CROP
                        linearlayout.addView(it)
                        val url = "https://vfiles.gtimg.cn/wupload/bbgtest.t_bbg_game/20220704_s92beu33h4lr8mkmwtjqyzvnifseo6tk.webp";
                        GlideUtil.loadWebp(it, url)
                    }
                } else {
                     LottieAnimationView(context).let {
                        it.scaleType = ImageView.ScaleType.CENTER_CROP
//                         it.setAnimation("ic_home_cover/data.json")
                         it.layoutParams = ViewGroup.LayoutParams(
                             ViewGroup.LayoutParams.MATCH_PARENT,
                             ViewGroup.LayoutParams.MATCH_PARENT
                         )
                         linearlayout.addView(it)
                         it.playAnimation()
                    }
                }
            }
            contentView!!.addView(linearlayout)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
