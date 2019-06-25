package yc.com.base

import android.content.Context
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream
import java.net.URLEncoder
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 *
 * Created by wanglin  on 2019/6/10 14:08.
 */
class Preference<T>(val context: Context,private val key: String, private val defaultValue: T) : ReadWriteProperty<Any?, T> {

    constructor(key: String,defaultValue: T):this(UIUtils.getContext(),key,defaultValue)

    private val pefers by lazy { context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE) }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(key, defaultValue)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(key, value)
    }

    private fun <U> findPreference(name: String, default: U): U = with(pefers) {
        val res: Any = when (default) {
            is String -> getString(name, default)
            is Long -> getLong(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException("This type can not be saved")
        }
        return res as U
    }

    private fun <A> putPreference(name: String, value: A) = with(pefers.edit()) {
        when (value) {//if语句 现在在kotlin中是表达式
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> putString(name, serialize(value))
        }.apply()
    }

    private fun <A> serialize(obj: A): String? {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
        objectOutputStream.writeObject(obj)
        var serStr = byteArrayOutputStream.toString("ISO-8859-1")
        serStr = URLEncoder.encode(serStr, "UTF-8")
        objectOutputStream.close()
        byteArrayOutputStream.close()

        return serStr
    }
}