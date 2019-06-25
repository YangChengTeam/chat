package yc.com.chat.index.model.engine

import android.content.Context
import com.alibaba.fastjson.TypeReference
import com.kk.securityhttp.domain.ResultInfo
import com.kk.securityhttp.engin.HttpCoreEngin
import rx.Observable
import yc.com.base.BaseEngine
import yc.com.chat.base.constant.index_url
import yc.com.chat.index.model.bean.IndexLoveInfo

/**
 *
 * Created by wanglin  on 2019/6/21 14:38.
 */
class IndexLoveEngine(context: Context) : BaseEngine(context) {

    fun getIndexLoveInfos(): Observable<ResultInfo<ArrayList<IndexLoveInfo>>> {

        return HttpCoreEngin.get(mContext).rxpost(index_url, object : TypeReference<ResultInfo<ArrayList<IndexLoveInfo>>>() {}.type, null,
                true, true, true) as Observable<ResultInfo<ArrayList<IndexLoveInfo>>>
    }
}