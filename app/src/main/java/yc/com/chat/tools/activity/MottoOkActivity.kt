package yc.com.chat.tools.activity

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.load.Key
import com.jakewharton.rxbinding.view.RxView
import com.kk.utils.ToastUtil
import kotlinx.android.synthetic.main.acitvity_mottook.*
import org.json.JSONObject
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R
import yc.com.chat.R.id.btn_top_left

import yc.com.chat.index.utils.UriUtil
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit


/**
 *
 * Created by wanglin  on 2019/6/19 15:12.
 */
class MottoOkActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {

    private var copytvIntId: Int = 0

    private var position = 1


    override fun getLayoutId(): Int {
        return R.layout.acitvity_mottook
    }

    override fun init() {
        intent?.let {
            val string = intent.getStringExtra("data_name")
            txt_top_title.text = "${string}个性签名"
            try {
                val inputStreamReader = InputStreamReader(assets.open("Alldata.json"), Key.STRING_CHARSET_NAME)
                val bufferedReader = BufferedReader(inputStreamReader)
                val stringBuilder = StringBuilder()
                while (true) {
                    val readLine = bufferedReader.readLine() ?: break
                    stringBuilder.append(readLine)
                }
                bufferedReader.close()
                inputStreamReader.close()
                val jSONArray = JSONObject(stringBuilder.toString()).getJSONArray("ALLdatas")
                for (i in 0 until jSONArray.length()) {
                    if (jSONArray.getJSONObject(i).getString("name").toString() == string) {
                        this.copytvIntId = jSONArray.getJSONObject(i).getInt("id")
                        getSingeData(this.copytvIntId, 0)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        initListener()
    }


    private fun initListener() {
        RxView.clicks(btn_top_left).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { finish() }
        RxView.clicks(copytv_Layout).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).text = copytv.text
            ToastUtil.toast2(this, "复制成功，一次只复制一条哦")
        }

        RxView.clicks(next_Layout).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            getSingeData(this.copytvIntId, this.position)
            this.position++
        }
    }


    private fun getSingeData(IcopytvIntId: Int, inum: Int) {
        try {
            val inputStreamReader = InputStreamReader(assets.open(UriUtil.DATA_SCHEME + IcopytvIntId + ".json"), Key.STRING_CHARSET_NAME)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder = StringBuilder()
            while (true) {
                val readLine = bufferedReader.readLine() ?: break
                stringBuilder.append(readLine)
            }
            bufferedReader.close()
            inputStreamReader.close()
            val jSONArray = JSONObject(stringBuilder.toString()).getJSONArray(UriUtil.DATA_SCHEME)
            var jSONObject: JSONObject? = null
            if (inum < jSONArray.length()) {
                jSONObject = jSONArray.getJSONObject(inum)
            } else {
                ToastUtil.toast2(this, "已经是最后一条了")
            }
            copytv.text = jSONObject?.getString("channelTitle")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun isStatusBarMateria(): Boolean {
        return false
    }

}