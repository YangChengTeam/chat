package yc.com.chat.combat.activity

import android.content.Context
import android.content.Intent
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_long_pic.*
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R
import yc.com.chat.combat.widget.MyScrollView


/**
 *
 * Created by wanglin  on 2019/6/18 08:26.
 */
class LongPicActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {


    private var content: String? = null
    private var name: String? = null

    companion object {
        fun startActivity(context: Context, name: String?, content: String?) {
            val intent = Intent(context, LongPicActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("content", content)
            context.startActivity(intent)
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_long_pic
    }

    override fun init() {
        intent?.let {
            name = intent.getStringExtra("name")
            content = intent.getStringExtra("content")
        }
        tv_pic_name.text = name
//        mainToolbar.setBasicInfo(null, getString(R.string.app_name), null)
        mainToolbar.init(this)
        initWebView()
        initListener()
    }


    fun initListener() {
        scrollView.setOnScrollChangedListener(object : MyScrollView.OnScrollChangedListener {
            override fun onScrolled(l: Int, t: Int, oldl: Int, oldt: Int) {

                if (t >= web.top) {
                    name?.let {
                        var index = name.toString().indexOf(":")
                        if (index == -1) {
                            index = name.toString().indexOf("：")
                        }

                        mainToolbar.setBasicInfo(null, name.toString().substring(index + 1), null)
                    }

                } else {
                    mainToolbar.setBasicInfo(null, "", null)
                }


            }
        })
    }

    private fun addImageClickListner() {
        web.loadUrl("javascript:(function(){var objs = document.getElementsByTagName(\"img\"); for(var i=0;i<objs.length;i++)  {    objs[i].onclick=function()      {          window.imagelistner.openImage(this.src);      }  }})()")
    }


    private fun imgReset() {
        web.loadUrl("javascript:(function(){var objs = document.getElementsByTagName('img'); for(var i=0;i<objs.length;i++)  {var img = objs[i];       img.style.maxWidth = '100%'; img.style.height = 'auto';  }})()")
    }


    private fun initWebView() {

        web.settings.javaScriptEnabled = true
        web.settings.loadWithOverviewMode = true
        web.settings.useWideViewPort = true
        web.settings.builtInZoomControls = true
        content?.let {
            //            web.loadDataWithBaseURL(null, content, "text/html", "utf-8", null)
            web.loadUrl(content?.substring(content!!.indexOf("http:"), content!!.indexOf(".jpg")) + ".jpg")
        }

        web.webViewClient = MyWebViewClient()
    }


    inner class MyWebViewClient : WebViewClient() {

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)

//            imgReset()
            addImageClickListner()
        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }


}