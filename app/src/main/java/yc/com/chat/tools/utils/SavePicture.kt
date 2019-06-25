package yc.com.chat.tools.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


/**
 *
 * Created by wanglin  on 2019/6/19 14:41.
 */
object SavePicture {
    fun saveImageToGallery(context: Context, bmp: Bitmap?) {
        val file = File(Environment.getExternalStorageDirectory(), "txqm")
        if (!file.exists()) {
            file.mkdir()
        }
        val str = System.currentTimeMillis().toString() + ".jpg"
        val file2 = File(file, str)
        try {
            val fileOutputStream = FileOutputStream(file2)
            bmp?.compress(CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e2: IOException) {
            e2.printStackTrace()
        }

        try {
            MediaStore.Images.Media.insertImage(context.contentResolver, file2.absolutePath, str, null)
            Toast.makeText(context, "保存成功,可在手机相册中查看!", Toast.LENGTH_SHORT).show()
        } catch (e3: FileNotFoundException) {
            Toast.makeText(context, "保存失败,请在手机设置中检查是否添加存储权限!", Toast.LENGTH_LONG).show()
            e3.printStackTrace()
        }

        context.sendBroadcast(Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.parse("file://" + file2.absolutePath)))
    }
}