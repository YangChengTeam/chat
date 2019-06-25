package yc.com.chat.constellation.model.bean

import android.content.Context
import com.alibaba.fastjson.TypeReference
import com.kk.securityhttp.engin.HttpCoreEngin
import rx.Observable
import yc.com.base.BaseEngine
import yc.com.chat.base.constant.constellation_url

/**
 *
 * Created by wanglin  on 2019/6/18 10:33.
 */
class ConstellationEngine(context: Context) : BaseEngine(context) {

    fun getConstellation(nan: String?, nv: String?): Observable<String> {

        return HttpCoreEngin.get(mContext).rxpost(constellation_url, object : TypeReference<String>() {}.type, mutableMapOf(
                "nan" to nan,
                "nv" to nv
        ), false, false, false) as Observable<String>
    }
}