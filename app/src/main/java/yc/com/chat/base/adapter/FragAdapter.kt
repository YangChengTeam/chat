package yc.com.chat.base.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


/**
 *
 * Created by wanglin  on 2019/6/17 16:03.
 */
class FragAdapter(fm: FragmentManager, private val mFragments: List<Fragment>) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return this.mFragments.size
    }

    override fun getItem(position: Int): Fragment {
        return this.mFragments[position]
    }
}
