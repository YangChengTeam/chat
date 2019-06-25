package yc.com.chat.base.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.alibaba.fastjson.JSON
import com.kk.securityhttp.domain.ResultInfo
import com.kk.securityhttp.net.contains.HttpConfig
import com.kk.utils.LogUtil
import com.vondear.rxtools.RxSPTool
import com.vondear.rxtools.RxTool
import rx.Subscriber
import yc.com.base.Preference
import yc.com.base.UIUtils
import yc.com.chat.base.constant.SpConstant
import yc.com.chat.base.model.bean.UserInfo

/**
 *
 * Created by wanglin  on 2019/6/19 08:35.
 */
@SuppressLint("StaticFieldLeak")
object UserInfoHelper {

    private var userData by Preference(SpConstant.USER, "")

    private var mUser: UserInfo? = null
    fun saveUser(user: UserInfo?) {


        this.mUser = user
        try {
            userData = JSON.toJSONString(user)
//            RxSPTool.putString(UIUtils.getContext(), SpConstant.USER, userData)
        } catch (e: Exception) {
            LogUtil.msg("error message-->" + e.message)
        }
    }

    fun getUser(): UserInfo? {
        if (mUser != null) {
            return mUser
        }
        try {
//            val str = RxSPTool.getString(UIUtils.getContext(), SpConstant.USER)
            Log.e("tag", userData)
            mUser = JSON.parseObject(userData, UserInfo::class.java)

        } catch (e: Exception) {
            LogUtil.msg("error message-->" + e.message)
        }
        return mUser
    }

    fun getUid(): String? {
        mUser?.let {
            return "${mUser?.id}"
        }
        return null
    }

    fun init(context: Context) {

        EngineUtils.init(context).subscribe(object : Subscriber<ResultInfo<UserInfo>>() {


            override fun onCompleted() {

            }

            override fun onError(e: Throwable?) {

            }

            override fun onNext(t: ResultInfo<UserInfo>?) {
                t?.let {
                    if (t.code == HttpConfig.STATUS_OK && t.data != null) {
                        saveUser(t.data)
                    }
                }

            }
        })

    }

}