package yc.com.chat.tools.activity

import com.kk.utils.ToastUtil
import yc.com.chat.tools.utils.SavePicture
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Bitmap

import android.os.AsyncTask
import android.os.Handler
import android.os.Message
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.util.Log
import com.jakewharton.rxbinding.view.RxView
import kotlinx.android.synthetic.main.activity_show_pic_page.*
import yc.com.base.*
import yc.com.chat.R
import yc.com.chat.base.constant.i_id
import yc.com.chat.base.constant.i_position
import yc.com.chat.tools.adapter.ShowPicPagerAdapter
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit


/**
 *
 * Created by wanglin  on 2019/6/21 12:06.
 */
class ShowPicPageActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>(), OnPageChangeListener, PermissionUIListener {


    private var mAdapter: ShowPicPagerAdapter? = null
    private var mBitmap: Bitmap? = null

    private val myHandler = C07531()

    private val names = arrayOf("宠物", "高端", "风景(旅游)", "开场", "美酒", "美食", "预选", "红酒晚会")
    private val num = intArrayOf(145, 26, 80, 14, 53, 255, 17, 4)

    private var f133p: Int = 0

    private var position: Int = 0

    private val urls = arrayOf("cw", "gd", "fj", "kc", "mj", "ms", "yx", "hj")


    override fun getLayoutId(): Int {
        return R.layout.activity_show_pic_page
    }

    override fun init() {
        intent?.let {
            this.f133p = intent.getIntExtra(i_position, 0)
            this.position = intent.getIntExtra(i_id, 0)
        }

        viewPager.offscreenPageLimit = 2
        mAdapter = ShowPicPagerAdapter(this, num[f133p], urls[f133p])
        viewPager.adapter = mAdapter
        viewPager.currentItem = position
        viewPager.addOnPageChangeListener(this)
        tv_num.text = (this.position + 1).toString() + "/" + this.num[this.f133p]

        mainToolbar.setBasicInfo(null, names[f133p], null)
        mainToolbar.init(this)
        Log.e("love", "http://staticfiles.xiazai63.com/" + this.urls[this.f133p] + "/" + (this.position + 1) + ".jpg")
        Task().execute("http://staticfiles.xiazai63.com/" + urls[f133p] + "/" + (position + 1) + ".jpg")
        initListener()
    }


    fun initListener() {
//        RxView.clicks(rl_back).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { finish() }
        RxView.clicks(ll_down).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            PermissionManager.instance.addPermissions(this@ShowPicPageActivity, this, PermissionGroup.getPermissionGroup(PermissionGroup.GroupType.STORAGE_GROUP))
        }

    }

    /* renamed from: com.rjjmc.newproinlove.activity.ShowPicPageActivity$1 */
    internal inner class C07531 : Handler() {

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                0 -> {
                    this@ShowPicPageActivity.position = msg.what
                    return
                }
                291 -> {
                    mBitmap = msg.obj as Bitmap
                }

                else -> return
            }
        }
    }


    internal inner class Task : AsyncTask<String, Int, Bitmap?>() {

        override fun doInBackground(vararg params: String): Bitmap? {
//            this@ShowPicPageActivity.mBitmap = this@ShowPicPageActivity.getImageInputStream(params[0])
            return this@ShowPicPageActivity.getImageInputStream(params[0])
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            val message = Message()
            message.what = 291
            message.obj = result
            myHandler.sendMessage(message)
        }
    }


    private fun getImageInputStream(imageurl: String): Bitmap? {
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
        tv_num.text = (position + 1).toString() + "/" + num[f133p]
        val message = Message()
        message.obj = Integer.valueOf(position)
        myHandler.sendEmptyMessage(0)
        myHandler.sendMessage(message)
        Task().execute("http://staticfiles.xiazai63.com/" + urls[f133p] + "/" + (position + 1) + ".jpg")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        PermissionManager.instance.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onPermissionGranted() {
        if (mBitmap != null) {
            SavePicture.saveImageToGallery(this, mBitmap)
        } else {
            ToastUtil.toast2(this, "下载失败,稍等片刻,重新尝试一次!")

        }
    }

    override fun onPermissionDenyed() {

    }

}