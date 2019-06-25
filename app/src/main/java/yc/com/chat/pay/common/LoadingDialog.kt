package yc.com.chat.pay.common

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import yc.com.chat.R


/**
 * Created by zhangkai on 2017/2/21.
 */

class LoadingDialog(context: Activity) : Dialog(context, R.style.customDialog) {

    private var ivCircle: ImageView
    private var tvMsg: TextView

    private val layoutID: Int
        get() = R.layout.dialog_loading

    init {
        val view = LayoutInflater.from(context).inflate(
                layoutID, null)
        this.setContentView(view)

        this.setCancelable(true)
        ivCircle = view.findViewById<View>(R.id.iv_circle) as ImageView
        tvMsg = view.findViewById<View>(R.id.tv_msg) as TextView
    }

    fun show(msg: String) {
        super.show()
        ivCircle.startAnimation(AnimationUtil.rotaAnimation())
        tvMsg.text = msg
    }


    override fun onStop() {
        super.onStop()
        ivCircle.clearAnimation()

    }
}
