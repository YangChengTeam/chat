package yc.com.chat.base.activity

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.KeyEvent
import android.widget.RelativeLayout
import com.jakewharton.rxbinding.view.RxView
import com.kk.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_main.*
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R
import yc.com.chat.base.adapter.FragAdapter
import yc.com.chat.base.constant.wx_num
import yc.com.chat.base.fragment.DialogFragmentWx
import yc.com.chat.base.widget.MainBottomBar
import yc.com.chat.combat.fragment.CombatFragment
import yc.com.chat.constellation.fragment.ConstellationFragment
import yc.com.chat.index.fragment.IndexFragment
import yc.com.chat.index.fragment.IndexFragment1
import yc.com.chat.tools.fragment.ToolsFragment
import java.util.concurrent.TimeUnit

/**
 *
 * Created by wanglin  on 2019/6/17 14:08.
 */
class MainActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>(), DialogFragmentWx.WxcopyActivityListener {


    private lateinit var fragmentList: ArrayList<Fragment>


    override fun getLayoutId(): Int {

        return R.layout.activity_main
    }

    override fun init() {
//        mainToolbar.setBasicInfo(null, getString(R.string.app_name), null)
        initMainToolbar()
        initViewPager()
        initBottomBar()
        initListener()
    }

    private fun initListener() {
        RxView.clicks(iv_weixin).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            takeWx()
        }
    }


    private fun takeWx() {
        val newInstance = DialogFragmentWx.newInstance("添加老师微信，一键解锁本软件所有功能及搜索功能，进高级学员群！", "老师微信号", wx_num, "添加老师微信进学员群，美女导师不定期指导，提高您的情商！")
        newInstance.setWxCopyActivityListener(this)
        newInstance.show(supportFragmentManager, "wx")
    }


    private fun getWechatApi() {
        try {
            val intent = Intent("android.intent.action.MAIN")
            val componentName = ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI")
            intent.addCategory("android.intent.category.LAUNCHER")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.component = componentName
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            ToastUtil.toast2(this, "检查到您手机没有安装微信")
        }
    }


    private fun initViewPager() {
        fragmentList = arrayListOf()

        fragmentList.add(IndexFragment1())
        fragmentList.add(CombatFragment())
        fragmentList.add(ToolsFragment())
        fragmentList.add(ConstellationFragment())


        viewpager.adapter = FragAdapter(supportFragmentManager, fragmentList)
        viewpager.offscreenPageLimit = 3
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(position: Int) {
                main_bottom_bar.setItem(position)
                if (position == 3) {
                    mainToolbar.setBgColor(ContextCompat.getColor(this@MainActivity, R.color.blue_3cc3e0))
                } else {
                    mainToolbar.setBgColor(ContextCompat.getColor(this@MainActivity, R.color.main_color))
                }
            }
        })
    }

    private fun initBottomBar() {
        main_bottom_bar.setOnItemSelectListener(object : MainBottomBar.OnItemSelectListener {
            override fun onItemSelect(position: Int) {
                viewpager.currentItem = position
            }
        })
    }

    override fun wxcopyActivityListener() {
        getWechatApi()
    }

    private fun initMainToolbar() {
        val rlHistory = mainToolbar.getRightView()?.findViewById<RelativeLayout>(R.id.rl_history)

        rlHistory?.let {
            RxView.clicks(rlHistory).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
                startActivity(Intent(this, HistoryListActivity::class.java))
            }
        }

    }



}