package com.example.pagetest

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pagetest.Constants.GLOBEL_ANIMTIONTYPE
import com.example.pagetest.Constants.USER_PAGE_ANIMATION
import com.example.pagetest.databinding.FragmentFirstBinding
import org.libpag.PAGFile
import org.libpag.PAGScaleMode
import org.libpag.PAGView
import kotlin.random.Random


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment: Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var recyclerView: RecyclerView? = null

    private var adapter:CustomAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerView
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView?.setLayoutManager(layoutManager)
        recyclerView?.setItemAnimator(null)

        var list: ArrayList<String> = ArrayList();
        for (i in 1 .. 2) {
            list.add("asdfasdf");
        }

        adapter = CustomAdapter(list) {
            list.add("asdfasdf")
            adapter?.notifyDataSetChanged()
        }
        recyclerView?.adapter = adapter

        adapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
class CustomAdapter(private val dataSet: ArrayList<String>, private val clickRunnable: Runnable) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View, private val clickRunnable: Runnable) : RecyclerView.ViewHolder(view) {
        var imageView1: ImageView? = null
        var imageView2: ImageView? = null
        var pageView1: PAGView? = null
        var pageView2: PAGView? = null
        val animationType: Int = GLOBEL_ANIMTIONTYPE

        init {
            // Define click listener for the ViewHolder's View.

            if (animationType == Constants.USER_PAGE_ANIMATION) {
                var contentView1: FrameLayout = view.findViewById(R.id.content_view_1)
                PAGView(view.context).let {
                    pageView1 = it
                    it.layoutParams =
                        RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    it.setScaleMode(PAGScaleMode.Zoom)
                    contentView1.addView(it)
                    it.setOnClickListener {
                        clickRunnable.run()
                        System.gc()
                    }
                }

                var contentView2: FrameLayout = view.findViewById(R.id.content_view_2)
                PAGView(view.context).let {
                    pageView2 = it
                    it.layoutParams =
                        RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    it.setScaleMode(PAGScaleMode.Zoom)
                    contentView2.addView(it)
                    it.setOnClickListener {
                        clickRunnable.run()
                        System.gc()
                    }
                }
            } else if (animationType == Constants.USER_WEBP_ANIMATION) {
                imageView1 = view.findViewById(R.id.image_view1)
                imageView1?.setOnClickListener {
                    clickRunnable.run()
                    System.gc()
                }
                imageView2 = view.findViewById(R.id.image_view2)
                imageView2?.setOnClickListener {
                    clickRunnable.run()
                    System.gc()
                }
            } else {

            }
        }
    }

    // Create new views (invoked by the layout manager)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.text_row_item, viewGroup, false)
        return ViewHolder(view, clickRunnable)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        if (viewHolder.animationType == Constants.USER_PAGE_ANIMATION) {
            viewHolder.pageView1?.let { playPageSource(it, position, true) }
            viewHolder.pageView2?.let { playPageSource(it, position, false) }
        } else if (viewHolder.animationType == Constants.USER_WEBP_ANIMATION) {
            val url = "https://vfiles.gtimg.cn/wupload/bbgtest.t_bbg_game/20220704_s92beu33h4lr8mkmwtjqyzvnifseo6tk.webp";
            viewHolder.imageView1?.let { GlideUtil.loadWebp(it, url) }
            viewHolder.imageView2?.let { GlideUtil.loadWebp(it, url) }
        } else {

        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    private fun createRandomColor(): Int {
        val random = Random(255)
        return Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255))
    }

    private fun playPageSource(pagView: PAGView,  position: Int, isLeft: Boolean) {
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

}