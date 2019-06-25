package yc.com.chat.constellation.datapicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

class DatePickerHelper {
    private int DAY_START;
    private int HOUR_START;
    private int MINUTE_START;
    private int MONTH_START;
    private int WEEK_START;
    private int YEAR_START;
    private ArrayList<String> dispalyTem = new ArrayList();
    private Date startDate = new Date();
    private ArrayList<Integer> tem = new ArrayList();
    private String[] weeks = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    private int yearLimt = 5;

    public enum Type {
        YEAR,
        MOTH,
        DAY,
        WEEK,
        HOUR,
        MINUTE
    }

    public DatePickerHelper() {
        init();
    }

    private void init() {
        Date date = this.startDate;
        this.YEAR_START = DateUtils.getYear(date);
        this.MONTH_START = DateUtils.getMoth(date);
        this.DAY_START = DateUtils.getDay(date);
        this.WEEK_START = DateUtils.getWeek(date);
        this.HOUR_START = DateUtils.getHour(date);
        this.MINUTE_START = DateUtils.getMinute(date);
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new DatePickerHelper().genDay(2016, 2)));
    }

    public int findIndextByValue(int value, Integer[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (value == arr[i].intValue()) {
                return i;
            }
        }
        return -1;
    }

    public Integer[] genArr(int size, boolean isZero) {
        this.tem.clear();
        int i = isZero ? 0 : 1;
        while (true) {
            if (i >= (isZero ? size : size + 1)) {
                return (Integer[]) this.tem.toArray(new Integer[0]);
            }
            this.tem.add(Integer.valueOf(i));
            i++;
        }
    }

    public Integer[] genDay() {
        return genDay(this.YEAR_START, this.MONTH_START);
    }

    public Integer[] genDay(int year, int moth) {
        Calendar instance = Calendar.getInstance();
        instance.set(year, moth, 1);
        instance.add(Calendar.DAY_OF_MONTH, -1);
        return genArr(Integer.parseInt(new SimpleDateFormat("d").format(instance.getTime())), false);
    }

    public Integer[] genHour() {
        return genArr(24, true);
    }

    public Integer[] genMinut() {
        return genArr(60, true);
    }

    public Integer[] genMonth() {
        return new Integer[]{Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5), Integer.valueOf(6), Integer.valueOf(7), Integer.valueOf(8), Integer.valueOf(9), Integer.valueOf(10), Integer.valueOf(11), Integer.valueOf(12)};
    }

    public Integer[] genYear() {
        int i;
        this.tem.clear();
        for (i = this.YEAR_START - this.yearLimt; i < this.YEAR_START; i++) {
            this.tem.add(Integer.valueOf(i));
        }
        this.tem.add(Integer.valueOf(this.YEAR_START));
        for (i = this.YEAR_START + 1; i < this.YEAR_START + this.yearLimt; i++) {
            this.tem.add(Integer.valueOf(i));
        }
        return (Integer[]) this.tem.toArray(new Integer[0]);
    }

    public String getDisplayStartWeek() {
        return getDisplayWeek(this.YEAR_START, this.MONTH_START, this.DAY_START);
    }

    public String[] getDisplayValue(Integer[] arr, String per) {
        this.dispalyTem.clear();
        for (Integer num : arr) {
            this.dispalyTem.add((num.intValue() < 10 ? "0" + num : "" + num) + per);
        }
        return (String[]) this.dispalyTem.toArray(new String[0]);
    }

    public String getDisplayWeek(int year, int moth, int day) {
        return this.weeks[DateUtils.getWeek(year, moth, day) - 1];
    }

    public int getToady(Type type) {
        switch (type) {
            case YEAR:
                return this.YEAR_START;
            case MOTH:
                return this.MONTH_START;
            case DAY:
                return this.DAY_START;
            case WEEK:
                return this.WEEK_START;
            case HOUR:
                return this.HOUR_START;
            case MINUTE:
                return this.MINUTE_START;
            default:
                return 0;
        }
    }

    public void setStartDate(Date date, int yearLimt) {
        this.startDate = date;
        this.yearLimt = yearLimt;
        if (this.startDate == null) {
            this.startDate = new Date();
        }
        init();
    }
}