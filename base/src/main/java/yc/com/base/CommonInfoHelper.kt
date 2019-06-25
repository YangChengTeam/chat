package yc.com.base

import android.content.Context
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.vondear.rxtools.RxLogTool
import java.lang.reflect.Type

/**
 * Created by wanglin  on 2018/2/9 11:34.
 */

object CommonInfoHelper {

    fun <T> getO(context: Context, key: String, type: Type, listener: onParseListener<T>?) {

        try {
            CacheUtils.readCache(context, key, object : CacheUtils.SubmitRunable() {
                override fun run() {
                    val json = this.json

                    UIUtils.post(Runnable {
                        json?.let {
                            val t = parseData<T>(json, type)

                            listener?.onParse(t)
                        }

                    })

                }
            }, object : CacheUtils.SubmitRunable() {
                override fun run() {
                    json?.let {
                        listener?.onFail(json as String)
                    }

                }
            })

        } catch (e: Exception) {
            RxLogTool.e(CommonInfoHelper::class.java.javaClass.name, "error:->>" + e.message)
        }

    }

    fun <T> setO(context: Context, t: T, key: String) {
        try {
            CacheUtils.writeCache(context, key, JSON.toJSONString(t))
        } catch (e: Exception) {
            RxLogTool.e(CommonInfoHelper::class.java.javaClass.name, "error:->>" + e.message)
        }

    }


    private fun <T> parseData(result: String, type: Type): T? {

        if (type.toString() == "java.lang.String") {
            return result as T
        }

        return if (type != null) {
            JSON.parseObject<T>(result, type)
        } else {
            JSON.parseObject<T>(result, object : TypeReference<T>() {

            }.type)
        }
    }


    interface onParseListener<T> {
        fun onParse(o: T?)

        fun onFail(json: String)
    }
}
