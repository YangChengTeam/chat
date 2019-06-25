package yc.com.chat.tools.activity

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.kk.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_picture_list2.*
import yc.com.base.BaseActivity
import yc.com.base.PermissionGroup
import yc.com.base.PermissionManager
import yc.com.base.PermissionUIListener
import yc.com.chat.R
import yc.com.chat.base.constant.i_id
import yc.com.chat.base.constant.name
import yc.com.chat.tools.adapter.PhotoAdapter
import yc.com.chat.tools.contract.PictureContract
import yc.com.chat.tools.fragment.PopupDialogActivity
import yc.com.chat.tools.model.bean.AutographListBean
import yc.com.chat.tools.model.bean.PhotoListBean
import yc.com.chat.tools.presenter.PicturePresenter
import yc.com.chat.tools.utils.SavePicture1
import yc.com.chat.tools.utils.SpacesItemDecoration
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL


/**
 *
 * Created by wanglin  on 2019/6/20 08:22.
 */
class PictureList2Activity : BaseActivity<PicturePresenter>(), PictureContract.View, PermissionUIListener {


    //    private var mBack: RelativeLayout? = null
    private var mBytes: ByteArray? = null

    private var mPhoto: String? = null
    private var popupDialogActivity: PopupDialogActivity? = null
    private var tail = ".gif"
    private var photoAdapter: PhotoAdapter? = null

    private var cId = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_picture_list2
    }

    override fun init() {

        intent?.let {
            val name = intent.getStringExtra(name)

            cId = intent.getIntExtra(i_id, 0)

            mainToolbar.setBasicInfo(null, name, null)
            mainToolbar.init(this)
        }


        mPresenter = PicturePresenter(this, this)


        photoAdapter = PhotoAdapter(null)
        small_recyclerView.layoutManager = GridLayoutManager(this, 3)
        small_recyclerView.adapter = photoAdapter
        small_recyclerView.addItemDecoration(SpacesItemDecoration(8))

        mPresenter?.getAllPhotoInfo("${cId}")
        initListener()
    }


    override fun showPictureInfo(t: AutographListBean) {

    }

    override fun showAllPhotoInfo(t: PhotoListBean) {
        photoAdapter?.setNewData(t.result_content)
    }

    fun initListener() {
        photoAdapter?.setOnItemClickListener { adapter, view, position ->


            mPhoto = photoAdapter?.getItem(position)?.photo
            mPhoto?.let {
                if (mPhoto!!.contains(tail)) {
                    this.tail = ".gif"
                } else {
                    this.tail = ".jpg"
                }

            }

            Task().execute(mPhoto)
            popupDialogActivity = PopupDialogActivity(this, object : View.OnClickListener {
                override fun onClick(v: View?) {
                    when (v?.id) {
                        R.id.popup_download /*2131624383*/ -> {
                            PermissionManager.instance.addPermissions(this@PictureList2Activity, this@PictureList2Activity, PermissionGroup.getPermissionGroup(PermissionGroup.GroupType.STORAGE_GROUP))
                            return
                        }
                        R.id.popup_cancel /*2131624384*/ -> {
                            popupDialogActivity?.dismiss()
                            return
                        }
                        else -> return
                    }
                }

            })
            popupDialogActivity?.showAtLocation(findViewById(R.id.small_recyclerView), 81, 0, 0)

        }
    }


    inner class Task : AsyncTask<String?, Int, ByteArray?>() {

        override fun doInBackground(vararg params: String?): ByteArray? {

            return this@PictureList2Activity.GetImageInputStream(params[0])
        }

        override fun onPostExecute(result: ByteArray?) {
            super.onPostExecute(result)
            mBytes = result

        }
    }


    fun GetImageInputStream(imageurl: String?): ByteArray? {
        var bArr: ByteArray? = null
        try {
            val httpURLConnection = URL(imageurl).openConnection() as HttpURLConnection
            httpURLConnection.connectTimeout = 6000
            httpURLConnection.doInput = true
            httpURLConnection.useCaches = false
            val inputStream = httpURLConnection.inputStream
            val byteArrayOutputStream = ByteArrayOutputStream()
            val bArr2 = ByteArray(10240)
            while (true) {
                val read = inputStream.read(bArr2, 0, 100)
                if (read > 0) {
                    byteArrayOutputStream.write(bArr2, 0, read)
                } else {
                    bArr = byteArrayOutputStream.toByteArray()
                    inputStream.close()
                    return bArr
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return bArr
        }

    }

    override fun onPermissionGranted() {
        if (mBytes != null) {
            SavePicture1.saveImageToGallery(this, mBytes, tail)
        } else {
            ToastUtil.toast2(this, "下载失败,稍等片刻,重新尝试一次!")
        }
        popupDialogActivity?.dismiss()
    }

    override fun onPermissionDenyed() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        PermissionManager.instance.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionManager.instance.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}