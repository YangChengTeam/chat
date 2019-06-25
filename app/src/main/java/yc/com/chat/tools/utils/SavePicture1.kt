package yc.com.chat.tools.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore.Images.Media
import android.widget.Toast
import com.kk.utils.ToastUtil
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

object SavePicture1 {
    fun saveImageToGallery(context: Context, bytes: ByteArray?, tail: String) {
        val file = File(Environment.getExternalStorageDirectory(), "txqm")
        if (!file.exists()) {
            file.mkdir()
        }
        val str = System.currentTimeMillis().toString() + tail
        val file2 = File(file, str)
        try {
            val fileOutputStream = FileOutputStream(file2)
            fileOutputStream.write(bytes)
            fileOutputStream.flush()
            fileOutputStream.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e2: IOException) {
            e2.printStackTrace()
        }

        try {
            Media.insertImage(context.contentResolver, file2.absolutePath, str, null)
            ToastUtil.toast2(context, "保存成功,可在手机相册中查看!")
        } catch (e3: FileNotFoundException) {
            ToastUtil.toast2(context, "保存失败,请在手机设置中检查是否添加存储权限!")
            e3.printStackTrace()
        }

        context.sendBroadcast(Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.parse("file://" + file2.absolutePath)))
    }
}