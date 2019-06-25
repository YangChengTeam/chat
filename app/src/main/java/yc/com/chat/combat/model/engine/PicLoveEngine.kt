package yc.com.chat.combat.model.engine

import android.content.Context
import com.alibaba.fastjson.TypeReference
import com.kk.securityhttp.engin.HttpCoreEngin
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import yc.com.base.BaseEngine
import yc.com.chat.base.constant.short_love_url
import yc.com.chat.combat.model.bean.PicContentBean

/**
 *
 * Created by wanglin  on 2019/6/17 19:00.
 */
class ShortLoveEngine(context: Context) : BaseEngine(context) {

    fun getShortLoveInfos(joketype: String): Observable<ArrayList<PicContentBean>> {

        return HttpCoreEngin.get(mContext).rxget(short_love_url, object : TypeReference<ArrayList<PicContentBean>>() {}.type, mutableMapOf(
                "joketype" to joketype
        ), false).observeOn(AndroidSchedulers.mainThread()) as Observable<ArrayList<PicContentBean>>
    }
}