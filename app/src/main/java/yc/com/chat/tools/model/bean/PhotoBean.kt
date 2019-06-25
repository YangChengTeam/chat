package yc.com.chat.tools.model.bean

/**
 *
 * Created by wanglin  on 2019/6/19 11:54.
 */
class PhotoBean {
    var result_code: Int = 0
    var result_content: List<ResultContentBean>? = null

    class ResultContentBean {
        var cid: Int = 0
        var cname: Any? = null
        var fid: Int = 0
        var fname: Any? = null
        var bigPhoto: String? = null
        /* renamed from: i_id */
        var id: Int = 0
        var smallPhoto: String? = null
    }
}