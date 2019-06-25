package yc.com.base

/**
 * Created by wanglin  on 2018/3/7 14:34.
 */

object BaseNetConstant {

    private val IS_DEBUG = false

    private val DEBUG_URL = "http://"

    private val BASE_URL = "https://answer.bshu.com/v1/"

    val baseUrl: String
        get() = if (IS_DEBUG) DEBUG_URL else BASE_URL


}
