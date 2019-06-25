package yc.com.chat.pay.common

import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation

/**
 * Created by zhangkai on 16/9/23.
 */
object AnimationUtil {

    fun rotaAnimation(): Animation {
        val ra = RotateAnimation(0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f)
        ra.interpolator = LinearInterpolator()
        ra.duration = 1500
        ra.repeatCount = -1
        ra.startOffset = 0
        ra.repeatMode = Animation.RESTART
        return ra
    }
}
