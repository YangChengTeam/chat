package yc.com.base

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.kk.utils.ToastUtil
import primary.answer.yc.com.base.R


import java.util.ArrayList
import java.util.Arrays

/**
 * Created by wanglin  on 2019/4/25 09:46.
 */
class PermissionManager private constructor() {

    private lateinit var permissionList: ArrayList<String>

    private var mActivity: Activity? = null
    private val REQUEST_CODE = 1024
    private var mListener: PermissionUIListener? = null
    private var dialog: AlertDialog? = null

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun addPermissions(activity: Activity, listener: PermissionUIListener, vararg permissions: Array<String>) {
        this.mActivity = activity
        this.mListener = listener
        if (permissions.isNotEmpty()) {
            permissionList = ArrayList()
            for (i in permissions.indices) {
                permissionList.addAll(Arrays.asList(*permissions[i]))
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                checkPermission(permissionList)
            else
                listener.onPermissionGranted()
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun checkPermission(permissionList: List<String>) {
        if (mActivity == null) {
            throw RuntimeException("必须在Activity中调用")
        }
        val unGrantPermissons = ArrayList<String>()

        for (permission in permissionList) {
            if (mActivity?.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED || mActivity!!.shouldShowRequestPermissionRationale(permission)) {
                unGrantPermissons.add(permission)
            }
        }

        // 权限都已经有了，那么直接调用SDK
        if (unGrantPermissons.size == 0) {
            //            listener.showAdv();

            //获得权限，想干什么干什么
            mListener?.onPermissionGranted()
        } else {
            // 请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限，如果获得权限就可以调用SDK，否则不要调用SDK。
            val requestPermissions = arrayOfNulls<String>(unGrantPermissons.size)
//            unGrantPermissons.toTypedArray()
            mActivity?.requestPermissions(requestPermissions, REQUEST_CODE)
        }
    }


    private fun hasAllPermissionsGranted(grantResults: IntArray): Boolean {
        for (grantResult in grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false
            }
        }
        return true
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE) {
            if (hasAllPermissionsGranted(grantResults)) {
                //            listener.showAdv();
                mListener?.onPermissionGranted()
                //获得权限
            } else {
                showMissingPermissionDialog(PermissionGroup.setPermissionTitle(permissionList))
                mListener?.onPermissionDenyed()
            }
        }
    }


    private fun startAppSettings() {
        // 如果用户没有授权，那么应该说明意图，引导用户去设置里面授权。
        Toast.makeText(mActivity, "应用缺少必要的权限！请点击\"权限\"，打开所需要的权限。", Toast.LENGTH_LONG).show()
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:" + mActivity?.packageName)
        mActivity?.startActivityForResult(intent, 320)
        dismissDialog()
        //        mActivity.finish();
    }

    private fun dismissDialog() {
        dialog?.let {
            if (dialog!!.isShowing) {
                dialog?.dismiss()
            }
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 320 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission(permissionList)
        }
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     */
    private fun showMissingPermissionDialog(permissionTitle: String) {
        dialog = AlertDialog.Builder(mActivity!!)
                .setTitle(R.string.tint)
                .setMessage("应用需要${permissionTitle}权限，请在-应用设置-权限-中打开")
                // 拒绝, 退出应用
                .setNegativeButton(R.string.base_cancel) { _, _ ->
                    dismissDialog()
                    mActivity?.finish()
                }

                .setPositiveButton(R.string.go_setting) { _, _ -> startAppSettings() }

                .setCancelable(false)

                .show()
    }

    companion object {
        val instance: PermissionManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            PermissionManager()

        }

    }


}
