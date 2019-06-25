package yc.com.chat.index.model.engine

import android.content.Context
import com.alibaba.fastjson.TypeReference
import com.kk.securityhttp.domain.ResultInfo
import com.kk.securityhttp.engin.HttpCoreEngin
import com.tencent.mm.opensdk.constants.Build
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import yc.com.base.BaseEngine
import yc.com.chat.base.constant.index_item_url
import yc.com.chat.base.constant.love_content_url
import yc.com.chat.base.constant.search_keyword_url
import yc.com.chat.base.constant.search_url
import yc.com.chat.base.utils.UserInfoHelper
import yc.com.chat.index.model.bean.LoveConteInfoWrapper
import yc.com.chat.index.model.bean.LoveContentInfo

/**
 *
 * Created by wanglin  on 2019/6/17 17:42.
 */
class LoveContentEngine(context: Context) : BaseEngine(context) {

    fun getContentInfos(id: String): Observable<ArrayList<LoveContentInfo>> {

        return HttpCoreEngin.get(mContext).rxget(love_content_url, object : TypeReference<ArrayList<LoveContentInfo>>() {}.type, mutableMapOf(
                "directoryid" to id
        ), false).observeOn(AndroidSchedulers.mainThread()) as Observable<ArrayList<LoveContentInfo>>
    }

    fun getLoveContentInfo(category_id: String, page: Int, page_size: Int): Observable<ResultInfo<ArrayList<LoveContentInfo>>> {
        return HttpCoreEngin.get(mContext).rxpost(index_item_url, object : TypeReference<ResultInfo<ArrayList<LoveContentInfo>>>() {}.type, mutableMapOf(
                "category_id" to category_id,
                "user_id" to UserInfoHelper.getUid(),
                "page" to "$page",
                "page_size" to "$page_size"
        ), true, true, true) as Observable<ResultInfo<ArrayList<LoveContentInfo>>>
    }


    fun searchContent(text: String?): Observable<ArrayList<LoveContentInfo>> {

        return HttpCoreEngin.get(mContext).rxget(search_keyword_url, object : TypeReference<ArrayList<LoveContentInfo>>() {}.type, mutableMapOf(
                "text" to text
        ), false).observeOn(AndroidSchedulers.mainThread()) as Observable<ArrayList<LoveContentInfo>>
    }

    fun searchLoveContent(keyword: String, page: Int, page_size: Int): Observable<ResultInfo<LoveConteInfoWrapper>> {


        return HttpCoreEngin.get(mContext).rxpost(search_url, object : TypeReference<ResultInfo<LoveConteInfoWrapper>>() {}.type, mutableMapOf(
                "keyword" to keyword,
                "user_id" to UserInfoHelper.getUid(),
                "page" to "$page",
                "page_size" to "$page_size"
        ), true, true, true) as Observable<ResultInfo<LoveConteInfoWrapper>>
    }

    fun searchLoveContent1(keyword: String, page: Int, page_size: Int): Observable<ResultInfo<ArrayList<LoveContentInfo>>> {


        return HttpCoreEngin.get(mContext).rxpost(search_url, object : TypeReference<ResultInfo<ArrayList<LoveContentInfo>>>() {}.type, mutableMapOf(
                "keyword" to keyword,
                "user_id" to UserInfoHelper.getUid(),
                "page" to "$page",
                "page_size" to "$page_size"
        ), true, true, true) as Observable<ResultInfo<ArrayList<LoveContentInfo>>>
    }

}