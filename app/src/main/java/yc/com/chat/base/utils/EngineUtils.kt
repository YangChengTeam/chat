package yc.com.chat.base.utils

import android.content.Context
import com.alibaba.fastjson.TypeReference
import com.kk.securityhttp.domain.ResultInfo
import com.kk.securityhttp.engin.HttpCoreEngin
import rx.Observable
import yc.com.chat.base.constant.index_init_url
import yc.com.chat.base.model.bean.UserInfo

/**
 *
 * Created by wanglin  on 2019/6/19 08:49.
 */
object EngineUtils {
    /**
     * 初始化接口
     */
    fun init(context: Context): Observable<ResultInfo<UserInfo>> {

        return HttpCoreEngin.get(context).rxpost(index_init_url, object : TypeReference<ResultInfo<UserInfo>>() {}.type, null,
                true, true, true) as Observable<ResultInfo<UserInfo>>
    }
}