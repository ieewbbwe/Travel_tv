package com.wisesoft.traveltv.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.android_mobile.core.utiles.Lg;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.RxLifecycle;
import com.wisesoft.traveltv.R;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;


/**
 * Created by mxh on 2017/9/8.
 * Describe：时间显示控件
 */

public class TimeTextView extends TextView {

    private BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    /*9月8日 星期五*/
    private String MM_dd_Week = "MM_dd";
    /*12:00*/
    private String HH_mm = "HH:mm";

    private String mFormat = HH_mm;
    private Calendar mCalendar;
    private Subscription mSubscription;

    public TimeTextView(Context context) {
        this(context, null);
    }

    public TimeTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TimeTextViewStyle);
        mFormat = ta.getString(R.styleable.TimeTextViewStyle_gFormat);
        mCalendar = Calendar.getInstance();
        ta.recycle();
        init();
    }

    private void init() {
        // Lg.print("TimeTextView", (long) o);
        mSubscription = Observable.interval(1000, TimeUnit.MILLISECONDS)
                .compose(RxLifecycle.bindView(lifecycleSubject))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                       // Lg.print("TimeTextView", (long) o);
                        if (MM_dd_Week.equals(mFormat)) {
                            setText(mCalendar.get(Calendar.MONTH) + 1 + "月"
                                    + mCalendar.get(Calendar.DAY_OF_MONTH) + "日 " + getWeekDay(mCalendar));
                        } else if (HH_mm.equals(mFormat)) {
                            setText(mCalendar.get(Calendar.HOUR_OF_DAY) + ":" + mCalendar.get(Calendar.MINUTE));
                        }
                    }
                });
    }

    private String getWeekDay(Calendar c) {
        if (c == null) {
            return "星期一";
        }
        if (Calendar.MONDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期一";
        }
        if (Calendar.TUESDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期二";
        }
        if (Calendar.WEDNESDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期三";
        }
        if (Calendar.THURSDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期四";
        }
        if (Calendar.FRIDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期五";
        }
        if (Calendar.SATURDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期六";
        }
        if (Calendar.SUNDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期日";
        }

        return "星期一";
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
    }
}
