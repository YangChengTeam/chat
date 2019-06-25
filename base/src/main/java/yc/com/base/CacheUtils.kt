package yc.com.base

import android.content.Context
import android.os.Environment
import android.text.TextUtils
import com.vondear.rxtools.RxFileTool
import java.io.File

/**
 * Created by wanglin  on 2018/2/9 11:10.
 */

object CacheUtils {

    fun writeCache(context: Context, key: String, json: String) {
        ThreadPoolUtils(ThreadPoolUtils.SingleThread, 5).execute {
            val path = FileUtils.createDir(makeBaseDir(context) + "/cache")
            RxFileTool.writeFileFromString("$path/$key", json, false)
        }

    }

    abstract class SubmitRunable : Runnable {

        var json: String? = null
    }


    @JvmOverloads
    fun readCache(context: Context, key: String, runable: SubmitRunable?, errorRunable: SubmitRunable? = null) {

        ThreadPoolUtils(ThreadPoolUtils.SingleThread, 5).execute {
            try {
                val path = FileUtils.createDir(makeBaseDir(context) + "/cache")
                val json = RxFileTool.readFile2String("$path/$key", "")
                if (!TextUtils.isEmpty(json)) {
                    if (runable != null) {
                        runable.json = json
                        runable.run()
                    }
                }
            } catch (e: Exception) {
                if (errorRunable != null) {
                    errorRunable.json = e.message
                    errorRunable.run()
                }
            }
        }

    }

    private fun makeBaseDir(context: Context): String {
        val pathname: String = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            Environment.getExternalStorageDirectory().toString() + "/" + context.packageName
        } else {
            context.externalCacheDir.toString() + "/" + context.packageName
        }

        val dir = File(pathname)
        if (!dir.exists()) {
            dir.mkdir()
        }
        return dir.absolutePath
    }

    fun makeBaseDir(context: Context, dirName: String): String {
        val baseDir = makeBaseDir(context)
        val dir = File("$baseDir/$dirName")
        if (!dir.exists()) {
            dir.mkdir()
        }
        return dir.absolutePath
    }
}
