package yc.com.chat.index.fragment

import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.jakewharton.rxbinding.view.RxView
import com.kk.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_love_text.*
import yc.com.base.*
import yc.com.chat.R
import yc.com.chat.base.utils.UserInfoHelper
import yc.com.chat.index.activity.LoveContentActivity
import yc.com.chat.index.activity.SearchContentActivity
import yc.com.chat.pay.activity.GoPayActivity
import java.util.concurrent.TimeUnit


/**
 *
 * Created by wanglin  on 2019/6/17 15:49.
 */
class IndexFragment : BaseFragment<BasePresenter<BaseEngine, BaseView>>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_love_text
    }

    private val isPay: Boolean = false

    private val isFree: String = "0"

    override fun init() {
//        mPresenter = PayPresenter(activity as BaseActivity<*>, this)


        click(name1, "2017102400000771")
        click(name2, "2017102400000772")
        click(name3, "2017102400000773")
        click(name4, "2017102400000774")
        click(name5, "2017102400000775")
        click(name6, "2017102400000776")
        click(name7, "2017102400000777")
        click(name8, "2017102400000778")
        click(name9, "2017102400000780")
        click(name10, "2017102400000788")
        click(name11, "2017102400000787")
        click(name12, "2017102400000789")
        click(name13, "2017102400000790")
        click(name14, "2017102400000791")
        click(name15, "2017102600000875")
        click(name16, "2017102600000876")
        click(name17, "2017102600000877")
        click(name18, "2017102600000878")
        click(name19, "2017102600000879")
        click(name20, "2017102600000880")
        click(name21, "2017102600000887")
        click(name22, "2017102600000881")
        click(name23, "2017102600000882")
        click(name24, "2017102600000883")
        click(name25, "2017102600000884")
        click(name26, "2017102600000885")
        click(name27, "2017102600000886")
        click(name28, "2017102600000888")
        click(name29, "2017102600000889")
        click(name30, "2017110200000961")
        click(name31, "2017110200000981")
        click(name32, "2017110200000982")
        click(name33, "2017110700001081")
        click(name34, "2017110700001082")
        click(name35, "2017110800001087")
        click(name36, "2017110800001088")
        click(name37, "2017110800001089")
        click(name38, "2017110800001090")
        click(name39, "2017110800001091")
        click(name40, "2017110800001092")
        click(name41, "2017110800001093")
        click(name42, "2017110800001094")
        click(name43, "2017110800001095")
        click(name44, "2017110800001096")
        click(name45, "2017110800001097")
        click(name46, "2017110800001098")
        click(name47, "2017110800001099")
        RxView.clicks(rl_search).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {

            var isVip = false
            val user = UserInfoHelper.getUser()
            user?.let {
                isVip = (1 == user.has_vip)
            }

//            val isVip = UserInfoHelper.getUser()?.has_vip
            if (isVip) {
                goSearch()
            } else {
                startActivity(Intent(activity, GoPayActivity::class.java))
            }

        }

    }

    private fun goSearch() {
        val obj = et_search.text.trim().toString()
        if (TextUtils.isEmpty(obj)) {
            ToastUtil.toast2(activity, "请输入搜索内容")
        } else {
            startActivity(Intent(activity, SearchContentActivity::class.java).putExtra("keyword", obj))
        }
    }


    private fun click(view: View, id: String) {
        RxView.clicks(view).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            LoveContentActivity.startActivity(activity as BaseActivity<*>, id)
        }
    }
}