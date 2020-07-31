package com.xuxiang.common.util;

import android.content.Context;
import android.graphics.Color;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created  on 2018-07-19
 *
 * @describe 统一选择器出处，方便统一修改
 */
public class PickerViewProvider {

    /**
     * 时间选择器-精确到分
     *
     * @param context
     * @param listener
     * @return
     */
    public static TimePickerView getTimePicker(Context context, OnTimeSelectListener listener) {
        return new TimePickerBuilder(context, listener)
                .setDividerColor(Color.parseColor("#0087FC"))
                .setCancelColor(Color.parseColor("#999999"))
                .setType(new boolean[]{true, true, true, true, true, false}).build();
    }

    public static TimePickerView getTimePicker1(Context context, OnTimeSelectListener listener) {
        return new TimePickerBuilder(context, listener)
                .setDividerColor(Color.parseColor("#0087FC"))
                .setCancelColor(Color.parseColor("#999999"))
                .setType(new boolean[]{true, true, false, false, false, false}).build();
    }

    public static TimePickerView getTimePicker2(Context context, OnTimeSelectListener listener) {
        return new TimePickerBuilder(context, listener)
                .setDividerColor(Color.parseColor("#0087FC"))
                .setCancelColor(Color.parseColor("#999999"))
                .setType(new boolean[]{true, true, true, true, true, false}).build();
    }

    /**
     * 获取当前时间之后的时间
     *
     * @param context
     * @param listener
     * @return
     */
    public static TimePickerView getAfterTimePicker(Context context, OnTimeSelectListener listener) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return new TimePickerBuilder(context, listener)
                .setRangDate(calendar, null)
                .setDividerColor(Color.parseColor("#0087FC"))
                .setCancelColor(Color.parseColor("#999999"))
                .setType(new boolean[]{true, true, true, true, true, false}).build();
    }


    public static TimePickerView getDayTimePicker(Context context, OnTimeSelectListener listener) {
        return new TimePickerBuilder(context, listener)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setDividerColor(Color.parseColor("#0087FC"))
                .setCancelColor(Color.parseColor("#999999"))
                .build();
    }

    public static TimePickerView getDayTimePickerBefor(Context context, OnTimeSelectListener listener, Date date) {
        Calendar calendar = null;
        if (date != null) {
            calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));

        }
        return new TimePickerBuilder(context, listener)
                .setRangDate(null, calendar)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setBgColor(Color.parseColor("#2e2f30"))
                .setTitleBgColor(Color.parseColor("#2e2f30"))
                .setSubmitColor(Color.parseColor("#ffffff"))
                .setCancelColor(Color.parseColor("#cccccc"))
                .setDividerColor(Color.parseColor("#E5E5E5"))
                .setTextColorCenter(Color.parseColor("#ffffff"))
                .build();
    }

    public static TimePickerView getDayMonthPickerBefor(Context context, OnTimeSelectListener listener, Date date) {
        Calendar calendar = null;
        if (date != null) {
            calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));

        }
        return new TimePickerBuilder(context, listener)
                .setRangDate(null, calendar)
                .setType(new boolean[]{true, true, false, false, false, false})
                .setSubmitColor(Color.parseColor("#3c68ff"))
                .setCancelColor(Color.parseColor("#cccccc"))
                .setDividerColor(Color.parseColor("#E5E5E5"))
                .setTextColorCenter(Color.parseColor("#333333"))
                .build();
    }

    public static TimePickerView getDayYearPickerBefor(Context context, OnTimeSelectListener listener, Date date) {
        Calendar calendar = null;
        if (date != null) {
            calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));

        }
        return new TimePickerBuilder(context, listener)
                .setRangDate(null, calendar)
                .setType(new boolean[]{true, false, false, false, false, false})
                .setSubmitColor(Color.parseColor("#3c68ff"))
                .setCancelColor(Color.parseColor("#cccccc"))
                .setDividerColor(Color.parseColor("#E5E5E5"))
                .setTextColorCenter(Color.parseColor("#333333"))
                .build();
    }


    public static TimePickerView getDayTimePickerBefor1(Context context, OnTimeSelectListener listener, Date date) {
        Calendar calendar = null;
        if (date != null) {
            calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);

        }
        return new TimePickerBuilder(context, listener)
                .setRangDate(null, calendar)
                .setType(new boolean[]{true, true, true, true, true, false})
                .setDividerColor(Color.parseColor("#0087FC"))
                .setCancelColor(Color.parseColor("#999999"))
                .build();
    }

    public static TimePickerView getDayTimePickerAfter(Context context, OnTimeSelectListener listener, Date date) {
        Calendar calendar = null;
        ;
        if (date != null) {
            calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }
        return new TimePickerBuilder(context, listener)
                .setRangDate(calendar, null)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setDividerColor(Color.parseColor("#0087FC"))
                .setCancelColor(Color.parseColor("#999999"))
                .build();
    }

    public static TimePickerView getDayTimePickerAfter1(Context context, OnTimeSelectListener listener, Date date) {
        Calendar calendar = null;
        if (date != null) {
            calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
        }
        return new TimePickerBuilder(context, listener)
                .setRangDate(calendar, null)
                .setType(new boolean[]{true, true, true, true, true, false})
                .setDividerColor(Color.parseColor("#0087FC"))
                .setCancelColor(Color.parseColor("#999999"))
                .build();
    }

    public static TimePickerView getDayTimePickerAfter2(Context context, OnTimeSelectListener listener, Date date) {
        Calendar calendar = null;
        if (date != null) {
            calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
        }
        return new TimePickerBuilder(context, listener)
                .setRangDate(calendar, null)
                .setType(new boolean[]{true, true, true, true, true, true})
                .setDividerColor(Color.parseColor("#0087FC"))
                .setCancelColor(Color.parseColor("#999999"))
                .build();
    }

    public static TimePickerView getDayTimePickerBefor(Context context, OnTimeSelectListener listener, boolean[] booleans, Date date) {
        Calendar calendar = null;
        if (date != null) {
            calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);

        }
        return new TimePickerBuilder(context, listener)
                .setDividerColor(Color.parseColor("#0087FC"))
                .setCancelColor(Color.parseColor("#999999"))
                .setType(booleans).build();
    }

    /**
     * 时间选择器-精确到分
     *
     * @param context
     * @param listener
     * @return
     */
    public static TimePickerView getTimePicker(Context context, OnTimeSelectListener listener, boolean[] booleans) {
        return new TimePickerBuilder(context, listener)
                .setDividerColor(Color.parseColor("#0087FC"))
                .setCancelColor(Color.parseColor("#999999"))
                .setType(booleans).build();
    }


    public static OptionsPickerView getNeedsTypeName(Context context, List name, OnOptionsSelectListener listener) {
        OptionsPickerView pvNoLinkOptions = new OptionsPickerBuilder(context, listener)
                .isRestoreItem(false)
                .setSubmitColor(Color.parseColor("#3c68ff"))
                .setCancelColor(Color.parseColor("#cccccc"))
                .setDividerColor(Color.parseColor("#E5E5E5"))
                .setTextColorCenter(Color.parseColor("#333333"))
                .build();
        pvNoLinkOptions.setPicker(name);
        return pvNoLinkOptions;
    }
}
