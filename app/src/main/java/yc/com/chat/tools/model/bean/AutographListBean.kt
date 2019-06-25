package yc.com.chat.tools.model.bean

/**
 *
 * Created by wanglin  on 2019/6/19 11:22.
 */
class AutographListBean {
    var result_code: Int = 0
    var result_content: List<ResultContentBean>? = null

    class ResultContentBean {
        var cid: Int = 0
        var cname: String? = null
        var fid: Int = 0
        var bigPhoto: String? = null
        var smallPhoto: String? = null
    }
}