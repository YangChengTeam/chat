package yc.com.chat.tools.fragment

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow

import yc.com.chat.R

class PopupDialogActivity(context: Activity, itemsOnClick: View.OnClickListener) : PopupWindow(context) {

    var popup_cancel: Button? = null
    var popup_download: Button? = null

    /* renamed from: com.rjjmc.newproinlove.utils.PopupDialogActivity$1 */
    inner class C08011 : View.OnClickListener {

        override fun onClick(v: View) {
            dismiss()
        }
    }

    init {
        val mView = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.popupwindow_dialog, null)
        popup_cancel = mView.findViewById<View>(R.id.popup_cancel) as Button
        popup_download = mView.findViewById<View>(R.id.popup_download) as Button

        popup_cancel?.setOnClickListener(C08011())
        popup_download?.setOnClickListener(itemsOnClick)
        contentView = mView
        width = ViewGroup.LayoutParams.MATCH_PARENT
        height = ViewGroup.LayoutParams.MATCH_PARENT
        isFocusable = true
        animationStyle = R.style.Animation
        setBackgroundDrawable(ColorDrawable(-1342177280))
    }


}