package yc.com.chat.constellation.fragment

import android.app.Dialog
import android.view.ViewGroup
import android.widget.Toast
import com.jakewharton.rxbinding.view.RxView
import kotlinx.android.synthetic.main.fragment_constell.*
import yc.com.base.BaseActivity
import yc.com.base.BaseFragment
import yc.com.chat.R
import yc.com.chat.constellation.activity.DetailActivity
import yc.com.chat.constellation.contract.ConsellationContract
import yc.com.chat.constellation.datapicker.DataPickerDialog
import yc.com.chat.constellation.presenter.ConsellationPresenter
import java.util.concurrent.TimeUnit

/**
 *
 * Created by wanglin  on 2019/6/17 15:59.
 */
class ConstellationFragment : BaseFragment<ConsellationPresenter>(), ConsellationContract.View {


    private var chooseDialog: Dialog? = null
    private val cons_xz = ArrayList<String>()


    private var nanStr: String? = null
    private var nvStr: String? = null


    override fun getLayoutId(): Int {
        return R.layout.fragment_constell
    }

    override fun init() {
//        val viewGroup = rootView?.parent as ViewGroup
//        viewGroup.removeView(rootView)
        initView()
        mPresenter = ConsellationPresenter(activity as BaseActivity<*>, this)
    }


    inner class onDataSelectedListener1 : DataPickerDialog.OnDataSelectedListener {

        override fun onCancel() {}

        override fun onDataSelected(itemValue: String, position: Int) {
            home_nan_tv.text = itemValue
            nanStr = position.toString() + ""
        }
    }

    internal inner class onDataSelectedListener2 : DataPickerDialog.OnDataSelectedListener {

        override fun onCancel() {}

        override fun onDataSelected(itemValue: String, position: Int) {
            home_nv_tv.text = itemValue
            nvStr = position.toString() + ""
        }
    }


    override fun showConstellationInfo(t: String) {
        DetailActivity.startActivity(activity as BaseActivity<*>, t)
    }


    private fun initListener() {
        RxView.clicks(home_nan_tv).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            showNanHourDialog(cons_xz)
        }

        RxView.clicks(home_nv_tv).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            showNvHourDialog(cons_xz)
        }

        RxView.clicks(home_set_btn).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            //            if (home_nan_tv.text.toString() == "" && home_nv_tv.text.toString() == "") {
//                Toast.makeText(activity, "请选择星座", Toast.LENGTH_SHORT).show()
//            } else {
            okhttp()
//            }
        }
    }

    private fun initView() {
        for (add in resources.getStringArray(R.array.constella_list)) {
            cons_xz.add(add)
        }
        initListener()
    }


    private fun okhttp() {
        mPresenter?.getConstellation(nanStr, nvStr)
    }


    private fun showNanHourDialog(list: List<String>) {
        chooseDialog = DataPickerDialog.Builder(activity).setData(list).setSelection(1).setTitle("取消").setOnDataSelectedListener(onDataSelectedListener1()).create()
        chooseDialog?.show()
    }

    private fun showNvHourDialog(list: List<String>) {
        chooseDialog = DataPickerDialog.Builder(activity).setData(list).setSelection(1).setTitle("取消").setOnDataSelectedListener(onDataSelectedListener2()).create()
        chooseDialog?.show()
    }


    override fun showLoadingDialog(mess: String) {
        (activity as BaseActivity<*>).showLoadingDialog(mess)
    }

    override fun dismissDialog() {
        (activity as BaseActivity<*>).dismissDialog()
    }

}