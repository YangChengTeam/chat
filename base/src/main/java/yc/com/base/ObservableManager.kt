package yc.com.base

import java.util.*

/**
 * Created by wanglin  on 2018/12/11 19:08.
 */
class ObservableManager private constructor() : Observable() {


    fun addMyObserver(observer: Observer) {
        addObserver(observer)
    }

    fun notifyMyObserver(`object`: Any?) {
        setChanged()
        notifyObservers(`object`)

    }

    fun deleteMyObserver(observer: Observer) {
        deleteObserver(observer)
    }

    fun deleteMyObservers() {
        deleteObservers()
    }

    companion object {

        val instance: ObservableManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            ObservableManager()
        }

    }

}

