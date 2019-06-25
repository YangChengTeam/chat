package yc.com.chat.base.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import yc.com.chat.R
import yc.com.chat.base.model.bean.HistoryListInfo
import java.text.SimpleDateFormat
import java.util.*


/**
 *
 * Created by wanglin  on 2019/6/19 09:27.
 */
class HistoryListAdapter(list: List<HistoryListInfo>?) : BaseQuickAdapter<HistoryListInfo, BaseViewHolder>(R.layout.listview_history_item, list) {
    override fun convert(helper: BaseViewHolder?, item: HistoryListInfo?) {
        helper?.let {
            item?.let {
                val sm = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                helper.setText(R.id.tv_num, item.orderNo)
                        .setText(R.id.tv_time, sm.format(Date(item.createTime!! * 1000)))
                        .setText(R.id.tv_isPay, item.getStringStatus())

            }
        }
    }

}