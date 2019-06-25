package yc.com.chat.index.widget

import android.content.Context
import android.text.style.ForegroundColorSpan
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.widget.EditText


/**
 *
 * Created by wanglin  on 2019/6/18 16:48.
 */
class MyTextView(context: Context, attrs: AttributeSet) : EditText(context, attrs) {

    fun setSpecifiedTextsColor(text: String, specifiedTexts: String, color: Int) {
        val arrayList = ArrayList<Int>()
        val length = specifiedTexts.length
        var str = text
        var i = 0
        var indexOf: Int
        do {
            indexOf = str.indexOf(specifiedTexts)
            if (indexOf != -1) {
                indexOf += i
                arrayList.add(Integer.valueOf(indexOf))
                i = indexOf + length
                str = text.substring(i)
                continue
            }
        } while (indexOf != -1)
        val spannableStringBuilder = SpannableStringBuilder(text)
        for (num in arrayList) {
            spannableStringBuilder.setSpan(ForegroundColorSpan(color), num, num + length, 33)
        }
        setText(spannableStringBuilder)
    }
}