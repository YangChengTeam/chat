package yc.com.chat.index.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 *
 * Created by wanglin  on 2019/6/18 17:39.
 */
object UIUtils {
    fun hideOrShowSoftInput(context: Context, et: EditText) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        //隐藏软键盘 //
        imm.hideSoftInputFromWindow(et.windowToken, 0)
////显示软键盘
//        imm.showSoftInputFromInputMethod(et_search.getWindowToken(), 0)
//        //切换软键盘的显示与隐藏
//        imm.toggleSoftInputFromWindow(et_search.getWindowToken(), 0, InputMethodManager.HIDE_NOT_ALWAYS)

    }
}