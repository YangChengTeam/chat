package yc.com.chat.tools.model.engine

import android.content.Context
import com.alibaba.fastjson.TypeReference
import com.kk.securityhttp.engin.HttpCoreEngin
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import yc.com.base.BaseEngine
import yc.com.chat.base.constant.all_photo_url
import yc.com.chat.base.constant.doutu_url
import yc.com.chat.tools.model.bean.AutographListBean
import yc.com.chat.tools.model.bean.PhotoListBean

/**
 *
 * Created by wanglin  on 2019/6/19 18:50.
 */
class PictureEngine(context: Context) : BaseEngine(context) {

    fun getPictureInfo(fid: String?): Observable<AutographListBean> {
        return HttpCoreEngin.get(mContext).rxget(doutu_url, object : TypeReference<AutographListBean>() {}.type, mutableMapOf(
                "fid" to fid
        ), false).observeOn(AndroidSchedulers.mainThread()) as Observable<AutographListBean>
    }


    fun getAllPhotoInfo(cid: String): Observable<PhotoListBean> {

        return HttpCoreEngin.get(mContext).rxget(all_photo_url, object : TypeReference<PhotoListBean>() {}.type, mutableMapOf(
                "cid" to cid
        ), false).observeOn(AndroidSchedulers.mainThread()) as Observable<PhotoListBean>
    }
}