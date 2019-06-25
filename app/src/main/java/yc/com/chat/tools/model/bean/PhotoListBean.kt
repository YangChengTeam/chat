package yc.com.chat.tools.model.bean

class PhotoListBean {
    var result_code: Int = 0
    var result_content: List<ResultContentBean>? = null

    class ResultContentBean {
        var cid: Int = 0
        var fid: Int = 0
        /* renamed from: id */
        var id: Int = 0
        var photo: String? = null
    }
}