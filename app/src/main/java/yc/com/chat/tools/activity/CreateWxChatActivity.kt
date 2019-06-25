package yc.com.chat.tools.activity

import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build.VERSION
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.MediaStore.Audio
import android.provider.MediaStore.Video
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import com.jakewharton.rxbinding.view.RxView
import com.kk.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_create_wx_chat.*
import kotlinx.android.synthetic.main.top_include.*
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R
import yc.com.chat.index.utils.UriUtil
import java.io.File
import java.util.concurrent.TimeUnit


/**
 *
 * Created by wanglin  on 2019/6/20 18:28.
 */
class CreateWxChatActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {
    private var bitmapdown: Bitmap? = null
    private var imageFile: File? = null
    private var imagePath: String? = null
    private val imageUri: Uri? = null


    override fun getLayoutId(): Int {
        return R.layout.activity_create_wx_chat
    }

    override fun init() {
//        tv_title.text = "微信单聊"

        mainToolbar.setBasicInfo(null,"微信单聊",null)
        mainToolbar.init(this)
//        RxView.clicks(rl_back).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
//            if (VERSION.SDK_INT >= 23) {
//                ToastUtil.toast2(this, "当前的版本号" + VERSION.SDK_INT)
//                if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
//                    startIcon()
//                    return@subscribe
//                }
//                ToastUtil.toast2(this, "执行了权限请求")
//                ActivityCompat.requestPermissions(this, arrayOf("android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"), 3)
//                return@subscribe
//            }
//
//        }
        startIcon()
    }


    fun getBitmapFromFile(dst: File?): Bitmap? {
        if (dst != null && dst.exists()) {
            val options = BitmapFactory.Options()
            options.inSampleSize = 2
            try {
                return BitmapFactory.decodeFile(dst.path, options)
            } catch (e: OutOfMemoryError) {
                e.printStackTrace()
            }

        }
        return null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        windowManager.defaultDisplay.getMetrics(DisplayMetrics())
        data?.let {
            val path = getPath(this, data.data)
            this.imageFile = File(path)
            this.imagePath = path
            this.bitmapdown = ThumbnailUtils.extractThumbnail(getBitmapFromFile(this.imageFile), 50, 50)
            iv_name_me.setImageBitmap(this.bitmapdown)
        }

    }


    fun startIcon() {
        val intent = Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        startActivityForResult(intent, 2)
    }


    fun getDataColumn(context: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?): String? {
        var cursor: Cursor? = null
        val str = "_data"
        return try {
            cursor = context.contentResolver.query(uri, arrayOf("_data"), selection, selectionArgs, null)
            if (cursor == null || !cursor.moveToFirst()) {
                cursor?.close()
                return null
            }
            cursor.getString(cursor.getColumnIndexOrThrow("_data"))
        } finally {
            cursor?.close()
        }
    }

    fun getPath(context: Context, uri: Uri?): String? {
        uri?.let {
            if ((if (VERSION.SDK_INT >= 19) 1 else 0) == 0 || !DocumentsContract.isDocumentUri(context, uri)) {
                return if (UriUtil.LOCAL_CONTENT_SCHEME.equals(uri.scheme, ignoreCase = true)) if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(context, uri, null, null) else if (UriUtil.LOCAL_FILE_SCHEME.equals(uri.scheme, ignoreCase = true)) uri.path else null
            } else {
                var split: Array<String>? = null
                if (isExternalStorageDocument(uri)) {
                    split = DocumentsContract.getDocumentId(uri).split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    return if ("primary".equals(split[0], ignoreCase = true)) Environment.getExternalStorageDirectory().path + "/" + split[1] else null
                } else if (isDownloadsDocument(uri)) {
                    return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(DocumentsContract.getDocumentId(uri)).toLong()), null, null)
                } else if (!isMediaDocument(uri)) {
                    return null
                } else {
                    val obj = DocumentsContract.getDocumentId(uri).split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
                    var uri2: Uri? = null
                    when (obj) {
                        "image" -> uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        "video" -> uri2 = Video.Media.EXTERNAL_CONTENT_URI
                        "audio" -> uri2 = Audio.Media.EXTERNAL_CONTENT_URI
                    }
                    val str = "_id=?"
                    split?.let {
                        return getDataColumn(context, uri2, "_id=?", arrayOf(split[1]))
                    }

                }
            }
        }
        return null

    }

    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.getAuthority()
    }

    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.getAuthority()
    }

    fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.getAuthority()
    }

    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.getAuthority()
    }

    override fun isStatusBarMateria(): Boolean {
        return false
    }
}