package yc.com.chat.tools.utils

import android.content.Context

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

object LcndUtil {
    fun getBin(c: Context, fileName: String): String {
        var e: IOException
        var str = ""
        try {
            val open = c.resources.assets.open(fileName)
            val bufferedReader = BufferedReader(InputStreamReader(open))
            var bufferedReader2: BufferedReader
            try {
                var str2: String? = ""
                while (true) {
                    str2 = bufferedReader.readLine()
                    if (str2 == null) {
                        break
                    }
                    str += (str2 + "\n")
                }
                bufferedReader2 = bufferedReader
            } catch (e2: IOException) {
                e = e2
                bufferedReader2 = bufferedReader
                e.printStackTrace()
                return str
            }

        } catch (e3: IOException) {
            e = e3
            e.printStackTrace()
            return str
        }

        return str
    }

    fun readAssetsTxt(context: Context, fileName: String): String {
        return try {
            val open = context.assets.open("$fileName.txt")
            val bArr = ByteArray(open.available())
            open.read(bArr)
            open.close()
            String(bArr)
        } catch (e: IOException) {
            e.printStackTrace()
            "读取错误，请检查文件名"
        }

    }
}