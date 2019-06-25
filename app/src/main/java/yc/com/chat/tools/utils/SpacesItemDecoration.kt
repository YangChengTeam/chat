package yc.com.chat.tools.utils

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class SpacesItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = this.space
        outRect.right = this.space
        outRect.bottom = this.space
        if (parent.getChildPosition(view) === 0) {
            outRect.top = this.space
        }
    }
}