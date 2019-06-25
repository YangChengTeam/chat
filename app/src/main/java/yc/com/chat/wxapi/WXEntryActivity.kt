package yc.com.chat.wxapi

import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram
import com.umeng.socialize.weixin.view.WXCallbackActivity

/**
 * Created by wanglin  on 2018/11/19 14:17.
 */
class WXEntryActivity : WXCallbackActivity() {
    fun onResp(resp: BaseResp) {

        if (resp.type == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
            val launchMiniProResp = resp as WXLaunchMiniProgram.Resp
            val extraData = launchMiniProResp.extMsg // 对应JsApi navigateBackApplication中的extraData字段数据
        }
    }
}
