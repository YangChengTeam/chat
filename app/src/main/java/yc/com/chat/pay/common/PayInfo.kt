package yc.com.chat.pay.common

import java.io.Serializable

/**
 * Created by zhangkai on 2017/4/14.
 */

class PayInfo : Serializable {

    //基本信息
    var partnerid: String? = null //商户id
    var appid: String? = null    //应用id

    //支付方式相关信息
    var payway_account_name: String? = null  //支付方式帐号名

    //原生支付宝支付信息
    var email: String? = null  //邮箱
    var privatekey: String? = null //密钥

    var notify_url: String? = null

    //原生微信渠道信息
    var mch_id: String? = null
    var nonce_str: String? = null
    var prepay_id: String? = null
    var result_code: String? = null
    var return_code: String? = null
    var return_msg: String? = null
    var sign: String? = null
    var trade_type: String? = null

    var timestamp: String? = null

    var md5signstr: String? = null

    var rsmd5: String? = null

    var starttime: String? = null

    companion object {

        const val serialVersionUID = -7260210533610464481L
    }
}
