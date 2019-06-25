package yc.com.chat.combat.fragment

import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import com.jakewharton.rxbinding.view.RxView
import kotlinx.android.synthetic.main.fragment_love_pic.*
import yc.com.base.BaseEngine
import yc.com.base.BaseFragment
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R
import yc.com.chat.base.adapter.FragAdapter
import java.util.concurrent.TimeUnit


/**
 *
 * Created by wanglin  on 2019/6/17 15:54.
 */
class CombatFragment : BaseFragment<BasePresenter<BaseEngine, BaseView>>() {
    private lateinit var fragmentList: ArrayList<Fragment>

    override fun getLayoutId(): Int {
        return R.layout.fragment_love_pic
    }

    override fun init() {
        fragmentList = arrayListOf()

        fragmentList.add(ShortLoveFragment())
        fragmentList.add(LongPicFragment())


        viewpager_pic.adapter = FragAdapter(childFragmentManager, fragmentList)
        initListener()

    }

    private fun initListener() {
        RxView.clicks(rl_short).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            //聊天教学
            viewpager_pic.currentItem = 0
            chooseShort()
        }
        RxView.clicks(rl_long).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            //恋爱解析
            viewpager_pic.currentItem = 1
            chooseLong()
        }

        viewpager_pic.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> chooseShort()
                    1 -> chooseLong()
                }
            }
        })

    }


    fun chooseLong() {
        rl_short.isSelected = false
        rl_long.isSelected = true
        view2.visibility = View.VISIBLE
        view1.visibility = View.INVISIBLE
//        view1.animate().
    }

    private fun chooseShort() {
       rl_short.isSelected = true
        rl_long.isSelected = false
        view1.visibility = View.VISIBLE
        view2.visibility = View.INVISIBLE
    }



}