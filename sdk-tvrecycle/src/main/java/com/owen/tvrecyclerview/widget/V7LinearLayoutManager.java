package com.owen.tvrecyclerview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.owen.tvrecyclerview.R;

/**
 * Created by owen on 2017/7/4.
 */

public class V7LinearLayoutManager extends LinearLayoutManager {
    public V7LinearLayoutManager(Context context) {
        super(context);
    }

    public V7LinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public V7LinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, 0);
    }

    @Override
    public boolean requestChildRectangleOnScreen(RecyclerView parent, View child, Rect rect, boolean immediate, boolean focusedChildVisible) {
        if(parent instanceof TvRecyclerView) {
            return parent.requestChildRectangleOnScreen(child, rect, immediate);
        }
        return super.requestChildRectangleOnScreen(parent, child, rect, immediate, focusedChildVisible);
    }

    public static class LayoutParams extends RecyclerView.LayoutParams {
        private static final int DEFAULT_INDEX = 0;
        private static final int DEFAULT_SCALE = 1;
        private static final int DEFAULT_SPAN = 1;

        public int rowSpan;
        public int colSpan;
        private int scale;
        public int sectionIndex;
        public boolean isSectionStart;
        public boolean isSuportIntelligentScrollStart;
        public boolean isSuportIntelligentScrollEnd;

        public LayoutParams(int width, int height) {
            super(width, height);
            init(null);
        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);

            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.TvRecyclerView_SpannableGridViewChild);
            colSpan = Math.max(
                    DEFAULT_SPAN, a.getInt(R.styleable.TvRecyclerView_SpannableGridViewChild_tv_colSpan, -1));
            rowSpan = Math.max(
                    DEFAULT_SPAN, a.getInt(R.styleable.TvRecyclerView_SpannableGridViewChild_tv_rowSpan, -1));
            a.recycle();

            init(null);
        }

        public LayoutParams(ViewGroup.LayoutParams other) {
            super(other);
            init(other);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams other) {
            super(other);
            init(other);
        }

        private void init(ViewGroup.LayoutParams other) {
            if (null != other && other instanceof MetroGridLayoutManager.LayoutParams) {
                final V7LinearLayoutManager.LayoutParams lp = (V7LinearLayoutManager.LayoutParams) other;
                this.scale = lp.scale;
                this.sectionIndex = lp.sectionIndex;
                this.isSectionStart = lp.isSectionStart;
                this.isSuportIntelligentScrollStart = lp.isSuportIntelligentScrollStart;
                this.isSuportIntelligentScrollEnd = lp.isSuportIntelligentScrollEnd;
            } else {
                rowSpan = DEFAULT_SPAN;
                colSpan = DEFAULT_SPAN;
                scale = DEFAULT_SCALE;
                sectionIndex = DEFAULT_INDEX;
                isSectionStart = false;
                isSuportIntelligentScrollStart = true;
                isSuportIntelligentScrollEnd = true;
            }
        }

        public int getRowSpan() {
            return rowSpan * scale;
        }

        public int getColSpan() {
            return colSpan * scale;
        }

        @Override
        public String toString() {
            return "[rowSpan="+rowSpan+" colSpan="+colSpan+ " sectionIndex="+sectionIndex+" scale="+scale+"]";
        }
    }
}
