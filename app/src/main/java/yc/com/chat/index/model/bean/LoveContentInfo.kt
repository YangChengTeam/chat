package yc.com.chat.index.model.bean

/**
 *
 * Created by wanglin  on 2019/6/17 17:45.
 */
class LoveContentInfo {

    /**
     * joke_id : 201711103834
     * joke_sort : 1
     * joke_name : 拍照搭讪法
     * time_str : 2017-11-10 12:15:30
     * update_time : 1510287330000
     * use_flag : 0
     * direc_id : 2017102400000771
     * joke_content :  男：能不能和我拍个照    <br></br>女：为什么？   <br></br>男：我要向我同事证明，天使是存在的（搭讪开场白）
     * joke_type : 4
     */

    var joke_id: String? = null
    var joke_sort: Int = 0
    var joke_name: String? = null
    var time_str: String? = null
    var update_time: Long = 0
    var use_flag: Int = 0
    var direc_id: String? = null
    var joke_content: String? = null
    var joke_type: String? = null

    /**
     * ans_sex : 2
     * chat_count : 10
     * chat_name : 你好呀
     * id : 70013416
     * quiz_sex : 1
     * has_vip : 0
     * details : [{"id":70051905,"dialogue_id":70013416,"content":"你好呀","ans_sex":"1","is_exec":0},{"id":70051906,"dialogue_id":70013416,"content":"你好！","ans_sex":"2","is_exec":0},{"id":70051907,"dialogue_id":70013416,"content":"我每天的工作需要我说几十次的你好，有时候真的感觉这俩字很奇怪。","ans_sex":"1","is_exec":0},{"id":70051908,"dialogue_id":70013416,"content":"怎么怪啦？","ans_sex":"2","is_exec":0},{"id":70051909,"dialogue_id":70013416,"content":"我有时候面对客户说这俩字的时候会突然不知道这两个字代表着什么意思。","ans_sex":"1","is_exec":0},{"id":70051910,"dialogue_id":70013416,"content":"你是做什么的？","ans_sex":"2","is_exec":0},{"id":70051911,"dialogue_id":70013416,"content":"卖交通工具的。","ans_sex":"1","is_exec":0},{"id":70051912,"dialogue_id":70013416,"content":"车？","ans_sex":"2","is_exec":0},{"id":70051913,"dialogue_id":70013416,"content":"我现在正在见一个客户，但是他在开会，他的会议室是那种上半部分是透明的，下半部分是磨砂的，所以我刚好能看见我客户的脑袋，他是一个秃头，我已经盯着这个秃头飘啊飘的半个小时了。","ans_sex":"1","is_exec":0},{"id":70051914,"dialogue_id":70013416,"content":"哈哈哈哈，在等他啊。","ans_sex":"2","is_exec":0}]
     */

    var ans_sex: Int = 0
    var chat_count: String? = null
    var chat_name: String? = null
    var id: Int = 0
    var quiz_sex: String? = null
    var is_vip: Int = 0
    var details: List<DetailsBean>? = null
    var detail: List<DetailsBean>? = null

    class DetailsBean {
        /**
         * id : 70051905
         * dialogue_id : 70013416
         * content : 你好呀
         * ans_sex : 1
         * is_exec : 0
         */
        var id: Int = 0
        var dialogue_id: Int = 0
        var content: String? = null
        var ans_sex: Int = 0
        var is_exec: Int = 0
    }


    /**
     * list : [{"ans_sex":"1","chat_count":"5","chat_name":"你吃了吗？","id":88203,"quiz_sex":"2","search_sort":1728,"has_vip":1,"detail":[]}]
     * search_buy_vip : 1
     */


    companion object {
        fun getSex(flag: Int): String {
            return when (flag) {
                1 -> "男"
                2 -> "女"
                else -> ""
            }
        }
    }


}