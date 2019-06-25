package yc.com.chat.tools.fragment

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_love_more.*
import yc.com.base.BaseEngine
import yc.com.base.BaseFragment
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R
import yc.com.chat.tools.activity.*
import yc.com.chat.tools.adapter.ToolsMoreAdapter


/**
 *
 * Created by wanglin  on 2019/6/17 15:57.
 */
class ToolsFragment : BaseFragment<BasePresenter<BaseEngine, BaseView>>() {


    private val classes = arrayOf<Class<*>>(AutographyActivity::class.java, MottoActivity::class.java, PictureActivity::class.java, WeiXinLqActivity::class.java, AlipayActivity::class.java, WeiXinHbActivity::class.java, ClockActivity::class.java, ScreenMainActivity::class.java, TxtListActivity::class.java, ShowPicActivity::class.java)

    private val names = arrayOf("头像签名", "个性签名", "斗图广场", "微信余额", "支付宝转账", "微信红包", "专属时钟", "装逼截图", "极限话术", "展示面")
    private val pics = intArrayOf(R.mipmap.hpic1, R.mipmap.hpic2, R.mipmap.hpic3, R.mipmap.hpic5, R.mipmap.hpic6, R.mipmap.hpic7, R.mipmap.p17, R.mipmap.wsjt, R.mipmap.love_p1, R.mipmap.show_true)
    private var toolsMoreAdapter: ToolsMoreAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_love_more
    }

    override fun init() {

        rv_all.layoutManager = GridLayoutManager(activity, 4)


        toolsMoreAdapter = ToolsMoreAdapter(pics, names.toList() as ArrayList<String>)
        rv_all.adapter = toolsMoreAdapter

        initListener()

    }

    fun initListener() {
        toolsMoreAdapter?.setOnItemClickListener { adapter, view, position ->
            startActivity(Intent(activity, classes[position]))
        }
    }


//    @Nullable
//    fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup, @Nullable savedInstanceState: Bundle): View {
//        val inflate = inflater.inflate(C0707R.layout.fragment_love_more, container, false)
//        this.isPay = (SPUtils.get(getActivity(), Constant.isPay, java.lang.Boolean.valueOf(false)) as Boolean).booleanValue()
//        this.isFree = SPUtils.get(getActivity(), Constant.isFree, "0")
//        initView(inflate)
//        initData()
//        return inflate
//    }
//
//    fun onResume() {
//        super.onResume()
//        this.isPay = (SPUtils.get(getActivity(), Constant.isPay, java.lang.Boolean.valueOf(false)) as Boolean).booleanValue()
//    }
}