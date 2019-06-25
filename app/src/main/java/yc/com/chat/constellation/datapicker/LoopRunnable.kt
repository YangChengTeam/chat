package yc.com.chat.constellation.datapicker

internal class LoopRunnable(val loopView: LoopView) : Runnable {

    override fun run() {
        this.loopView.loopListener.onItemSelect(LoopView.getSelectItem(this.loopView))
    }
}