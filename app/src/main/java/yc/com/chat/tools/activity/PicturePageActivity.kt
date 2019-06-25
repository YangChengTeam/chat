package yc.com.chat.tools.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Handler
import android.os.Message
import android.support.v4.view.ViewPager.OnPageChangeListener
import com.jakewharton.rxbinding.view.RxView
import com.kk.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_picture_page.*
import yc.com.base.*
import yc.com.chat.R
import yc.com.chat.base.constant.i_flag
import yc.com.chat.base.constant.i_list
import yc.com.chat.base.constant.i_position
import yc.com.chat.tools.adapter.MyViewPagerAdapter
import yc.com.chat.tools.utils.SavePicture
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit


/**
 *
 * Created by wanglin  on 2019/6/19 14:08.
 */
class PicturePageActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>(), OnPageChangeListener, PermissionUIListener {


    private var datas: List<String>? = null

    private var flag: String? = ""
    private var mAdapter: MyViewPagerAdapter? = null

    private var mBitmap: Bitmap? = null

    private val myHandler = MyHandler()


    /* renamed from: p */
    private var position: Int = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_picture_page
    }

    override fun init() {
        intent?.let {
            datas = intent.getStringArrayListExtra(i_list)
            position = intent.getIntExtra(i_position, 0)
            flag = intent.getStringExtra(i_flag)
        }

        viewPager.offscreenPageLimit = 2
        mAdapter = MyViewPagerAdapter(this.datas, this, true)
        viewPager.adapter = mAdapter
        viewPager.currentItem = position
        viewPager.addOnPageChangeListener(this)
        tv_num.text = (this.position + 1).toString() + "/" + this.datas?.size
        Task().execute(datas?.get(this.position))
        initListener()
    }


    fun applyPermission() {
        PermissionManager.instance.addPermissions(this, this, PermissionGroup.getPermissionGroup(PermissionGroup.GroupType.STORAGE_GROUP))
    }

    fun initListener() {
        RxView.clicks(iv_big_back).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { finish() }

        RxView.clicks(ll_down).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {

            applyPermission()

        }
    }

    /* renamed from: com.rjjmc.newproinlove.activity.PicturePageActivity$1 */
    internal inner class MyHandler : Handler() {

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                0 -> {
                    this@PicturePageActivity.position = msg.what
                    return
                }
                291 -> {
                    val result = msg.obj
                    result?.let {
                        mBitmap = result as Bitmap
                    }

                }
                else -> return
            }
        }
    }

    inner class Task : AsyncTask<String?, Int, Bitmap?>() {

        override fun doInBackground(vararg params: String?): Bitmap? {

            return this@PicturePageActivity.GetImageInputStream(params[0])
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            val message = Message()
            message.what = 291
            message.obj = result
            myHandler.sendMessage(message)
        }
    }

    fun GetImageInputStream(imageurl: String?): Bitmap? {
        var bitmap: Bitmap? = null
        return try {
            val httpURLConnection = URL(imageurl).openConnection() as HttpURLConnection
            httpURLConnection.connectTimeout = 6000
            httpURLConnection.doInput = true
            httpURLConnection.useCaches = false
            val inputStream = httpURLConnection.inputStream
            bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            bitmap
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        PermissionManager.instance.onActivityResult(requestCode, resultCode, data)

    }


    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        tv_num.text = (position + 1).toString() + "/" + this.datas?.size
        val message = Message()
        message.obj = Integer.valueOf(position)
        this.mHandler?.sendEmptyMessage(0)
        this.mHandler?.sendMessage(message)
        Task().execute(this.datas?.get(position))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionManager.instance.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }


    override fun onPermissionGranted() {
        if (this.mBitmap != null) {
            SavePicture.saveImageToGallery(this, this.mBitmap)
        } else {
            ToastUtil.toast2(this, "下载失败,稍等片刻,重新尝试一次!")
        }

    }

    override fun onPermissionDenyed() {
//        showDialogTipUserGoToAppSettting()
    }

}