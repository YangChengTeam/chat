package yc.com.base

import android.Manifest
import java.util.ArrayList

/**
 * Created by wanglin  on 2019/4/25 13:30.
 */
object PermissionGroup {


    enum class GroupType {
        CAMERA_GROUP, CALENDAR_GROUP, CONTACT_GROUP, LOCATION_GROUP, MICROPHONE_GROUP, PHONE_GROUP, SENSORS_GROUP, SMS_GROUP, STORAGE_GROUP
    }


    fun getPermissionGroup(type: GroupType): Array<String> {

        return when (type) {
            PermissionGroup.GroupType.CAMERA_GROUP -> arrayOf(Manifest.permission.CAMERA)
            PermissionGroup.GroupType.CALENDAR_GROUP -> arrayOf(Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR)
            PermissionGroup.GroupType.CONTACT_GROUP -> arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.GET_ACCOUNTS)
            PermissionGroup.GroupType.LOCATION_GROUP -> arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            PermissionGroup.GroupType.MICROPHONE_GROUP -> arrayOf(Manifest.permission.RECORD_AUDIO)
            PermissionGroup.GroupType.PHONE_GROUP -> arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG, Manifest.permission.ADD_VOICEMAIL, Manifest.permission.USE_SIP, Manifest.permission.PROCESS_OUTGOING_CALLS)
            PermissionGroup.GroupType.SENSORS_GROUP -> arrayOf(Manifest.permission.BODY_SENSORS)
            PermissionGroup.GroupType.SMS_GROUP -> arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_WAP_PUSH, Manifest.permission.RECEIVE_MMS)
            PermissionGroup.GroupType.STORAGE_GROUP -> arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }


    fun setPermissionTitle(permissions: ArrayList<String>?): String {
        var title = " "
        permissions?.let {
            for (i in permissions) {
                when (i) {
                    Manifest.permission.CAMERA -> title += "相机 "
                    Manifest.permission.READ_CALENDAR -> title += "日历 "
                    Manifest.permission.READ_CONTACTS -> title += "联系人 "
                    Manifest.permission.ACCESS_FINE_LOCATION -> title += "位置 "
                    Manifest.permission.RECORD_AUDIO -> title += "录音 "
                    Manifest.permission.READ_PHONE_STATE -> title += "电话 "
                    Manifest.permission.SEND_SMS -> title += "短信 "
                    Manifest.permission.READ_EXTERNAL_STORAGE -> title += "存储 "
                }
            }
        }

        return title
    }

}
