package yc.com.chat.base

import android.os.Build
import android.support.multidex.MultiDexApplication
import com.kk.securityhttp.domain.GoagalInfo
import com.kk.securityhttp.net.contains.HttpConfig
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import yc.com.base.UIUtils
import yc.com.chat.base.utils.UserInfoHelper
import java.util.HashMap

/**
 *
 * Created by wanglin  on 2019/6/18 17:23.
 */
class ChatApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        UIUtils.init(this)
        Observable.just("").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            GoagalInfo.get().init(this)
            setHttpDefaultParams()
        }

    }


    private fun setHttpDefaultParams() {
        //设置http默认参数
        var agent_id = "1"
        val params = HashMap<String, String>()
        if (GoagalInfo.get().channelInfo != null && GoagalInfo.get().channelInfo.agent_id != null) {
            params["from_id"] = GoagalInfo.get().channelInfo.from_id + ""
            params["author"] = GoagalInfo.get().channelInfo.author + ""
            agent_id = GoagalInfo.get().channelInfo.agent_id
        }
        params["agent_id"] = agent_id
        params["ts"] = System.currentTimeMillis().toString() + ""
        params["device_type"] = "2"
        params["app_id"] = "3"
        params["imeil"] = GoagalInfo.get().uuid
        val sv = if (android.os.Build.MODEL.contains(android.os.Build.BRAND))
            android.os.Build.MODEL + " " + android
                    .os.Build.VERSION.RELEASE
        else
            Build.BRAND + " " + android
                    .os.Build.MODEL + " " + android.os.Build.VERSION.RELEASE
        params["sys_version"] = sv
        if (GoagalInfo.get().packageInfo != null) {
            params["app_version"] = GoagalInfo.get().packageInfo.versionCode.toString() + ""
        }
        HttpConfig.setPublickey("-----BEGIN PUBLIC KEY-----\n" +
                "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEA5KaI8l7xplShIEB0Pwgm\n" +
                "MRX/3uGG9BDLPN6wbMmkkO7H1mIOXWB/Jdcl4/IMEuUDvUQyv3P+erJwZ1rvNsto\n" +
                "hXdhp2G7IqOzH6d3bj3Z6vBvsXP1ee1SgqUNrjX2dn02hMJ2Swt4ry3n3wEWusaW\n" +
                "mev4CSteSKGHhBn5j2Z5B+CBOqPzKPp2Hh23jnIH8LSbXmW0q85a851BPwmgGEan\n" +
                "5HBPq04QUjo6SQsW/7dLaaAXfUTYETe0HnpLaimcHl741ftGyrQvpkmqF93WiZZX\n" +
                "wlcDHSprf8yW0L0KA5jIwq7qBeu/H/H5vm6yVD5zvUIsD7htX0tIcXeMVAmMXFLX\n" +
                "35duvYDpTYgO+DsMgk2Q666j6OcEDVWNBDqGHc+uPvYzVF6wb3w3qbsqTnD0qb/p\n" +
                "WxpEdgK2BMVz+IPwdP6hDsDRc67LVftYqHJLKAfQt5T6uRImDizGzhhfIfJwGQxI\n" +
                "7TeJq0xWIwB+KDUbFPfTcq0RkaJ2C5cKIx08c7lYhrsPXbW+J/W4M5ZErbwcdj12\n" +
                "hrfV8TPx/RgpJcq82otrNthI3f4QdG4POUhdgSx4TvoGMTk6CnrJwALqkGl8OTfP\n" +
                "KojOucENSxcA4ERtBw4It8/X39Mk0aqa8/YBDSDDjb+gCu/Em4yYvrattNebBC1z\n" +
                "ulK9uJIXxVPi5tNd7KlwLRMCAwEAAQ==\n" +
                "-----END PUBLIC KEY-----")
        HttpConfig.setDefaultParams(params)

        UserInfoHelper.init(this)


    }
}