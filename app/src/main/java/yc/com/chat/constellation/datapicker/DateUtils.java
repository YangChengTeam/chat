package yc.com.chat.constellation.datapicker;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



class DateUtils {
    DateUtils() {
    }

    public static Date getDate(int year, int moth, int day, int hour, int minute) {
        Calendar instance = Calendar.getInstance();
        instance.set(year, moth - 1, day, hour, minute);
        return instance.getTime();
    }

    public static int getDay(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return instance.get(Calendar.DAY_OF_MONTH);
    }

    public static int getHour(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return instance.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return instance.get(Calendar.MINUTE);
    }

    public static int getMoth(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return instance.get(Calendar.MONTH) + 1;
    }

    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getWeek(int year, int moth, int day) {
        Calendar instance = Calendar.getInstance();
        instance.set(year, moth - 1, day);
        return instance.get(Calendar.DAY_OF_WEEK);
    }

    public static int getWeek(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return instance.get(Calendar.DAY_OF_WEEK);
    }

    public static int getYear(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return instance.get(Calendar.YEAR);
    }

    public static void main(String[] args) {
        try {
            System.out.println(getHour(new SimpleDateFormat("yyyy-MM-dd HH").parse("2016-12-15 12")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}