package yc.com.chat.index.model.bean

/**
 *
 * Created by wanglin  on 2019/6/21 14:35.
 */
class IndexLoveInfo {

    /**
     * id : 1
     * name : 魅力开场
     * parent_id : 0
     * _level : 1
     * children : [{"id":2,"name":"搭讪开场","parent_id":1,"_level":"2"},{"id":3,"name":"话题重启","parent_id":1,"_level":"2"},{"id":4,"name":"表情话术","parent_id":1,"_level":"2"},{"id":5,"name":"趣味开场","parent_id":1,"_level":"2"},{"id":6,"name":"勾起好奇","parent_id":1,"_level":"2"}]
     */

    var id: Int = 0
    var name: String? = null
    var parent_id: Int = 0
    var _level: String? = null
    var children: ArrayList<ChildrenBean>? = null

    class ChildrenBean {
        /**
         * id : 2
         * name : 搭讪开场
         * parent_id : 1
         * _level : 2
         */

        var id: Int = 0
        var name: String? = null
        var parent_id: Int = 0
        var _level: String? = null
    }
}