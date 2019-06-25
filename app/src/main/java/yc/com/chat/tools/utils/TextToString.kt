package yc.com.chat.tools.utils

import android.content.Context

/**
 *
 * Created by wanglin  on 2019/6/19 10:53.
 */
object TextToString {
    fun texttostring(txtName: String, context: Context): String {
        val str = ""
        try {
            val open = context.assets.open(txtName)
            val bArr = ByteArray(open.available())
            open.read(bArr)
            open.close()
            return String(bArr)
        } catch (e: Throwable) {
            throw RuntimeException(e)
        }

    }
}