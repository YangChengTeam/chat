package yc.com.chat.base.widget

import android.content.Context
import android.util.AttributeSet
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.jakewharton.rxbinding.view.RxView
import com.umeng.analytics.MobclickAgent
import kotlinx.android.synthetic.main.layout_main_bottombar.view.*
import yc.com.base.BaseView
import yc.com.chat.R
import java.util.concurrent.TimeUnit

/**
 * Created by wanglin  on 2018/11/23 11:56.
 */
class MainBottomBar(context: Context, attrs: AttributeSet?) : BaseView(context, attrs) {


    private var onItemSelectListener: OnItemSelectListener? = null

    override fun getLayoutId(): Int {
        return R.layout.layout_main_bottombar
    }

    override fun init() {
        super.init()
        setItem(0)
        //        mainItemWall.setSelect(true);
        //        startAnimation();
        RxView.clicks(ll_index_container).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            setItem(0)
            //                mainItemWall.setSelected(true);
            onItemSelectListener?.onItemSelect(0)


        }
        RxView.clicks(ll_constellation_container).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            setItem(3)
            //                mainItemMine.setSelected(true);

            onItemSelectListener?.onItemSelect(3)

        }

        RxView.clicks(ll_chat_container).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            setItem(1)
            //                mainItemCheck.setSelected(true);
            onItemSelectListener?.onItemSelect(1)

        }

        RxView.clicks(ll_tool_container).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            setItem(2)
            // TODO: 2019/2/13 添加福利点击事件统计
//            MobclickAgent.onEvent(mContext, "welfare_id", "福利")

            onItemSelectListener?.onItemSelect(2)

        }

    }

//    private fun startAnimation() {
//        val animation1 = AnimationUtil.INSTANCE.startRippleAnimation(1.0f, 1.05f, 1.0f, 1.05f, 1.0f, 0.5f, 1500, Animation.INFINITE)
//        ivCarton1!!.startAnimation(animation1)
//        val animation2 = AnimationUtil.INSTANCE.startRippleAnimation(1.05f, 1.1f, 1.05f, 1.1f, 0.5f, 1.0f, 1500, Animation.INFINITE)
//        ivCarton2!!.startAnimation(animation2)
//    }
//
//    private fun stopAnimation() {
//        ivCarton1!!.clearAnimation()
//        ivCarton2!!.clearAnimation()
//    }

    private fun resetState() {
        mainItem_index.setSelect(false)
        mainItem_chat.setSelect(false)
        mainItem_tool.setSelect(false)
        mainItem_constellation.setSelect(false)
    }

    fun setItem(position: Int) {

        resetState()
        when (position) {
            0 -> mainItem_index.setSelect(true)
            1 -> mainItem_chat.setSelect(true)
            2 -> mainItem_tool.setSelect(true)
            3 -> mainItem_constellation.setSelect(true)
        }

    }

    fun setOnItemSelectListener(onItemSelectListener: MainBottomBar.OnItemSelectListener) {
        this.onItemSelectListener = onItemSelectListener
    }

    interface OnItemSelectListener {
        fun onItemSelect(position: Int)
    }


}
