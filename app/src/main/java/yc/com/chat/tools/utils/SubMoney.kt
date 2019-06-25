package yc.com.chat.tools.utils

object SubMoney {
    fun subMoney(money: String?): String? {
        money?.let {

            if (!money.contains(".")) {
                return "$money.00"
            }
            val indexOf = money.indexOf(".")
            val substring = money.substring(indexOf, money.length - 1)
            return if (substring.isEmpty()) money + "00" else if (substring.length == 1) money + "0" else if (substring.length != 2) money.substring(0, indexOf + 3) else money
        }
        return money
    }
}