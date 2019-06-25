package yc.com.base

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_DIP
import android.view.*
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.main_tool_bar.view.*
import primary.answer.yc.com.base.R

/**
 *
 * Created by wanglin  on 2019/5/6 14:47.
 * 多功能toolbar控件，统一管理app头部
 */
class MainToolBar(context: Context, attrs: AttributeSet) : BaseView(context, attrs) {
    private var mActivity: AppCompatActivity? = null
    //是否在正中间显示文字或者其他布局
    private var mShowCenter: Boolean = false
    //是否显示右边图标或其他布局
    private var mShowRight: Boolean = false
    private var mIvIcon: ImageView? = null
    //右边布局，可以自定义，但必须保留一个id为iv_icon的ImageView，可以不显示
    private var mResourceId = R.layout.toolbar_imageview
    //中间布局，自定义
    private var mCenterLayout = R.layout.toolbar_center_title

    //默认背景颜色
    private var bgColor: Int = ContextCompat.getColor(context, R.color.red_f14343)

    //中间布局的文字控件，默认
    private var mTvTitle: TextView? = null

    //右边布局根布局
    private var rightView: View? = null

    //中间布局根布局
    private var centerView: View? = null

    //是否添加paddingTop
    private var mIsPaddingTop = true

    //中间布局是否占满整个屏幕
    private var isCenterFull = false

    private var mLeftPadding: Float = 0f


    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.MainToolBar)

        try {
            mShowCenter = ta.getBoolean(R.styleable.MainToolBar_isShowCenter, mShowCenter)
            mShowRight = ta.getBoolean(R.styleable.MainToolBar_isShowRight, mShowRight)
            mIsPaddingTop = ta.getBoolean(R.styleable.MainToolBar_is_padding_top, mIsPaddingTop)
            mResourceId = ta.getResourceId(R.styleable.MainToolBar_right_layout, mResourceId)
            mCenterLayout = ta.getResourceId(R.styleable.MainToolBar_center_layout, mCenterLayout)
            bgColor = ta.getColor(R.styleable.MainToolBar_main_background, bgColor)
            isCenterFull = ta.getBoolean(R.styleable.MainToolBar_is_center_full, isCenterFull)

            mLeftPadding = ta.getDimension(R.styleable.MainToolBar_padding_left, TypedValue.applyDimension(COMPLEX_UNIT_DIP, mLeftPadding, context.resources.displayMetrics))
            toolBar.title = ""
            if (mLeftPadding.toInt() != 0) toolBar.setPadding(mLeftPadding.toInt(), 0, 0, 0)
            if (mShowRight) {
                right_container.visibility = View.VISIBLE
                rightView = LayoutInflater.from(context).inflate(mResourceId, right_container, true)//右边布局
                mIvIcon = rightView?.findViewById(R.id.iv_icon)

            }
            if (mShowCenter) {
                centerView = LayoutInflater.from(context).inflate(mCenterLayout, center_container, true)//加载中间布局
                mTvTitle = centerView?.findViewById(R.id.tv_title)
            }

            root_container.setBackgroundColor(bgColor)
            if (!mIsPaddingTop) root_container.setPadding(0, 0, 0, 0)
            if (isCenterFull) {
                val layoutParams = center_container.layoutParams
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                center_container.layoutParams = layoutParams
            }

            showRightVisble(mShowRight)

        } finally {
            ta.recycle()
        }
    }

    /**
     * 返回右边布局
     */
    fun getRightView(): View? {
        return rightView
    }


    /**
     * 返回中间布局
     */
    fun getCenterView(): View? {
        return centerView
    }

    override fun getLayoutId(): Int {
        return R.layout.main_tool_bar
    }

    /**
     * 设置toolbar的基本信息，包括logo，title，subtitle
     * 设置setSupportActionBar后，setTitle 要用getActionBar.setTitle才能生效
     * 这个方法放在init之前调用
     */
    fun setBasicInfo(logo: Int?, title: CharSequence, subTitle: CharSequence?) {
        if (mShowCenter) {

            mTvTitle?.visibility = View.VISIBLE
            toolBar.title = ""
            mTvTitle?.text = title
        } else {
            mTvTitle?.visibility = View.GONE
            logo?.let { toolBar.logo = ContextCompat.getDrawable(context, logo) }
            toolBar.title = title//
            subTitle?.let { toolBar.subtitle = subTitle }
        }
    }


    fun setBgColor(bgColor: Int) {
        this.bgColor = bgColor
        root_container.setBackgroundColor(bgColor)
    }

    /**
     * 设置右边图标和点击事件
     */

    fun showRightIconRes(resId: Int?, listener: OnClickListener? = null) {
        resId?.let {
            mIvIcon?.setImageResource(resId)
        }
        right_container.setOnClickListener(listener)
    }

    fun showRightIconPath(path: String?, listener: OnClickListener? = null) {
        mIvIcon?.let {
            Glide.with(context).load(path).into(mIvIcon!!)
        }
        right_container.setOnClickListener(listener)
    }

    /**
     * 设置toolbar的返回键和点击事件，以及menu的点击事件
     */
    fun init(activity: AppCompatActivity, isShowNavigation: Boolean = true, navigationRes: Int = R.drawable.back, clickListener: OnClickListener?, listener: Toolbar.OnMenuItemClickListener?) {
        this.mActivity = activity
        activity.setSupportActionBar(toolBar)
        if (isShowNavigation) {
            toolBar.setNavigationIcon(navigationRes)
            toolBar.setNavigationOnClickListener(clickListener)
        }
        listener?.let {
            toolBar.setOnMenuItemClickListener(listener)
        }

    }

    fun init(activity: AppCompatActivity) {
        this.init(activity, clickListener = OnClickListener { activity.finish() }, listener = null)
    }


    /**
     * 加载toolbar的menu菜单，需在相应的activity中重写该方法
     * menuId 是自己想填充的menu样式，可以自定义，也可以使用本例中的menu布局
     */
    fun onCreateOptionsMenu(menu: Menu?, menuId: Int): Boolean {

        if (mShowRight) return false
        mActivity?.let {
            mActivity?.menuInflater?.inflate(menuId, menu)
            return true
        }
        return false
    }

    fun showRightVisble(isShowRight: Boolean) {
        this.mShowRight = isShowRight
        right_container.visibility = if (isShowRight) View.VISIBLE else View.GONE
    }


}