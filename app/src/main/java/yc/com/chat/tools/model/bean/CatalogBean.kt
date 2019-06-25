package yc.com.chat.tools.model.bean

/**
 *
 * Created by wanglin  on 2019/6/19 10:38.
 */
class CatalogBean {
    var result_code: Int = 0
    var result_content: List<ResultContentBean>? = null

    class ResultContentBean {
        /* renamed from: i_id */
        var id: Int = 0
        var name: String? = null
    }
}