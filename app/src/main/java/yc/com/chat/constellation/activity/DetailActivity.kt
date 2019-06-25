package yc.com.chat.constellation.activity


import android.content.Context
import android.content.Intent
import android.view.View
import kotlinx.android.synthetic.main.detail_activity.*
import org.json.JSONException
import org.json.JSONObject
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R


/**
 *
 * Created by wanglin  on 2019/6/18 10:19.
 */
class DetailActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {

    /* renamed from: jo */
    private var f126jo: JSONObject? = null
    private var jo2: JSONObject? = null
    private var lqxy: String? = null
    private var pdzs: String? = null

    private var tcdj: String? = null

    private var tqbd: String? = null

    private var f127xz: String? = null
    private var xzpd: String? = null

    companion object {
        fun startActivity(context: Context, response: String) {
            val intent = Intent()
            intent.setClass(context, DetailActivity::class.java)
            intent.putExtra("response", response)
            context.startActivity(intent)
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.detail_activity
    }

    override fun init() {
        intent?.let {
            try {
                this.f126jo = JSONObject(intent.getStringExtra("response"))
                this.jo2 = f126jo?.getJSONObject("result_content")
                this.xzpd = jo2?.getString("xzpd")
                this.f127xz = jo2?.getString("xz")
                this.lqxy = jo2?.getString("lqxy")
                this.tcdj = jo2?.getString("tcdj")
                this.pdzs = jo2?.getString("pdzs")
                this.tqbd = jo2?.getString("tqbd")
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        initView()

    }

    private fun initView() {

//        title_text.text = this.xzpd
        xzpd?.let {
            mainToolbar.setBasicInfo(null, xzpd!!, null)
            mainToolbar.init(this, true, R.mipmap.fanhui, View.OnClickListener { finish() }, null)
        }

        detail_tv1.text = f127xz
        detail_tv2.text = lqxy
        detail_tv3.text = tcdj
        detail_tv6.text = tqbd?.replace("谈情必读：", "")
        this.pdzs = pdzs?.replace("\n", ",")
        val split = pdzs?.split(",".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
        split?.let {
            detail_tv4.text = split[0]
            tab_tv1.text = split[1]
            tab_tv2.text = split[2]
            tab_tv3.text = split[3]
            tab_tv4.text = split[4]
        }

    }


}