package yc.com.chat.base.model.engine

import android.content.Context
import com.alibaba.fastjson.TypeReference
import com.kk.securityhttp.domain.ResultInfo
import com.kk.securityhttp.engin.HttpCoreEngin
import rx.Observable
import yc.com.base.BaseEngine
import yc.com.chat.base.constant.order_list_url
import yc.com.chat.base.model.bean.HistoryListInfo

/**
 *
 * Created by wanglin  on 2019/6/19 09:11.
 */
class HistoryListEngine(context: Context) : BaseEngine(context) {

    fun getHistoryList(): Observable<ResultInfo<ArrayList<HistoryListInfo>>> {

        return HttpCoreEngin.get(mContext).rxpost(order_list_url, object : TypeReference<ResultInfo<ArrayList<HistoryListInfo>>>() {}.type,
                null, true, true, true) as Observable<ResultInfo<ArrayList<HistoryListInfo>>>
    }
}