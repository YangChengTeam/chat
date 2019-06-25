package yc.com.chat.constellation.datapicker

enum class DateType private constructor(val format: String) {
    TYPE_ALL("yyyy-MM-dd E hh:mm"),
    TYPE_YMDHM("yyyy-MM-dd hh:mm"),
    TYPE_YMDH("yyyy-MM-dd hh"),
    TYPE_YMD(DateUtil.ymd),
    TYPE_HM("hh:mm")
}