package yc.com.chat.constellation.datapicker;

import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
    public static final String ymd = "yyyy-MM-dd";
    public static final String ymdhms = "yyyy-MM-dd HH:mm:ss";

    public static String formatDate(long milliseconds, String format) {
        String str = "";
        try {
            return new SimpleDateFormat(format).format(new Date(milliseconds));
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    public static String formatDate(String date, String format) {
        String str = date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.format(simpleDateFormat.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    public static Date formatDateStr(String date, String format) {
        Date date2 = null;
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            return date2;
        }
    }

    public static Calendar getCalendar(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return instance;
    }

    public static List<Integer> getDateForString(String date) {
        String[] split = date.split("-");
        List<Integer> arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(Integer.parseInt(split[0])));
        arrayList.add(Integer.valueOf(Integer.parseInt(split[1])));
        arrayList.add(Integer.valueOf(Integer.parseInt(split[2])));
        return arrayList;
    }

    public static String getDayWeek(int year, int month, int day) {
        Calendar instance = Calendar.getInstance();
        instance.set(year, month, day);
        Log.d("DateView", "DateView:First:" + instance.getFirstDayOfWeek());
        switch (instance.get(7)) {
            case 1:
                return "周日";
            case 2:
                return "周一";
            case 3:
                return "周二";
            case 4:
                return "周三";
            case 5:
                return "周四";
            case 6:
                return "周五";
            case 7:
                return "周六";
            default:
                return "";
        }
    }

    public static int getFirstDayWeek(int year, int month) {
        Calendar instance = Calendar.getInstance();
        instance.set(year, month, 1);
        Log.d("DateView", "DateView:First:" + instance.getFirstDayOfWeek());
        return instance.get(7);
    }

    public static int getMonthDays(int year, int month) {
        switch (month + 1) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2:
                return ((year % 4 != 0 || year % 100 == 0) && year % 400 != 0) ? 28 : 29;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return -1;
        }
    }

    public static String getToday() {
        Calendar instance = Calendar.getInstance();
        int i = instance.get(2) + 1;
        return instance.get(1) + "-" + (i > 9 ? Integer.valueOf(i) : "0" + i) + "-" + instance.get(5);
    }

    public static String getTomorrow() {
        Calendar instance = Calendar.getInstance();
        instance.add(5, 1);
        int i = instance.get(2) + 1;
        return instance.get(1) + "-" + (i > 9 ? Integer.valueOf(i) : "0" + i) + "-" + instance.get(5);
    }

    public static int getYear() {
        return Calendar.getInstance().get(1);
    }

    public static String monthNumToMonthName(String month) {
        return "1".equals(month) ? "一月份" : "2".equals(month) ? "二月份" : "3".equals(month) ? "三月份" : "4".equals(month) ? "四月份" : "5".equals(month) ? "五月份" : "6".equals(month) ? "六月份" : "7".equals(month) ? "七月份" : "8".equals(month) ? "八月份" : "9".equals(month) ? "九月份" : "10".equals(month) ? "十月份" : "11".equals(month) ? "十一月份" : "12".equals(month) ? "十二月份" : month;
    }
}