package yc.com.base

/**
 * Created by wanglin  on 2019/4/25 10:19.
 */
interface PermissionUIListener {

    fun onPermissionGranted() //权限全获取

    fun onPermissionDenyed() //权限被拒绝
}
