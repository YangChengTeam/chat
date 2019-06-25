package yc.com.chat.wxapi


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.kk.utils.ToastUtil
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import yc.com.chat.pay.common.IPayCallback
import yc.com.chat.pay.common.IPayImpl
import yc.com.chat.pay.common.OrderInfo


class WXPayEntryActivity : Activity(), IWXAPIEventHandler {

    private var api: IWXAPI? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        api = WXAPIFactory.createWXAPI(this, IPayImpl.appid) //appid需换成商户自己开放平台appid
        api!!.handleIntent(intent, this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    override fun onReq(req: BaseReq) {}

    override fun onResp(resp: BaseResp) {

        if (resp.type == ConstantsAPI.COMMAND_PAY_BY_WX && IPayImpl.uOrderInfo != null && IPayImpl.uiPayCallback != null) {
            // resp.errCode == -1 原因：支付错误,可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等
            // resp.errCode == -2 原因 用户取消,无需处理。发生场景：用户不支付了，点击取消，返回APP
            if (resp.errCode == 0)
            //支付成功
            {
                runOnUiThread { ToastUtil.toast2(this@WXPayEntryActivity, "支付成功") }
                (IPayImpl.uOrderInfo as OrderInfo).message = "支付成功"
                (IPayImpl.uiPayCallback as IPayCallback).onSuccess(IPayImpl.uOrderInfo as OrderInfo)
            } else if (resp.errCode == -1)
            // 支付错误
            {
                runOnUiThread { ToastUtil.toast2(this@WXPayEntryActivity, "支付错误") }
                (IPayImpl.uOrderInfo as OrderInfo).message = "支付错误"
                (IPayImpl.uiPayCallback as IPayCallback).onFailure(IPayImpl.uOrderInfo as OrderInfo)

            } else if (resp.errCode == -2)
            // 支付取消
            {
                runOnUiThread { ToastUtil.toast2(this@WXPayEntryActivity, "支付取消") }
                (IPayImpl.uOrderInfo as OrderInfo).message = "支付取消"
                (IPayImpl.uiPayCallback as IPayCallback).onFailure(IPayImpl.uOrderInfo as OrderInfo)

            } else {
                runOnUiThread { ToastUtil.toast2(this@WXPayEntryActivity, "支付失败") }
                (IPayImpl.uOrderInfo as OrderInfo).message = "支付失败"
                (IPayImpl.uiPayCallback as IPayCallback).onFailure(IPayImpl.uOrderInfo as OrderInfo)
            }
            IPayImpl.uOrderInfo = null
            IPayImpl.uiPayCallback = null
            finish()
        }
    }
}