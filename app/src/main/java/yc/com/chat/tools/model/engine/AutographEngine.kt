package yc.com.chat.tools.model.engine

import android.content.Context
import com.alibaba.fastjson.TypeReference
import com.kk.securityhttp.engin.HttpCoreEngin
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import yc.com.base.BaseEngine
import yc.com.chat.base.constant.childtype_url
import yc.com.chat.base.constant.photo_url
import yc.com.chat.tools.model.bean.AutographListBean
import yc.com.chat.tools.model.bean.PhotoBean

/**
 *
 * Created by wanglin  on 2019/6/19 11:21.
 */
class AutographEngine(context: Context) : BaseEngine(context) {

    fun getAutographInfo(fid: String?): Observable<AutographListBean> {
        return HttpCoreEngin.get(mContext).rxget(childtype_url, object : TypeReference<AutographListBean>() {}.type, mutableMapOf(
                "fid" to fid
        ), false).observeOn(AndroidSchedulers.mainThread()) as Observable<AutographListBean>
    }


    fun getPhotoInfo(cid: String): Observable<PhotoBean> {
        return HttpCoreEngin.get(mContext).rxget(photo_url, object : TypeReference<PhotoBean>() {}.type, mutableMapOf(
                "cid" to cid
        ), false).observeOn(AndroidSchedulers.mainThread()) as Observable<PhotoBean>
    }
}