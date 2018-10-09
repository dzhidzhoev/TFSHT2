package com.staplex.tfsht2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

class MetroViewGroup extends ViewGroup {

    public MetroViewGroup(Context context) {
        this(context, null);
    }

    public MetroViewGroup(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public MetroViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, null, 0, 0);
    }

    public MetroViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        int childWidth = parentWidth - getPaddingLeft() - getPaddingRight();
        int childHeight = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();

        // Measure children
        measureChildren(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.AT_MOST),
                MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.AT_MOST));

        // Measure result
        int measuredHeight = getPaddingTop() + getPaddingBottom();
        // Current line width
        int lineWidth = 0;
        // Max width of each line
        int maxLineWidth = parentWidth - getPaddingLeft() - getPaddingBottom();
        // Max child height in current line
        int maxLineHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            int deltaWidth = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            if (deltaWidth + lineWidth > maxLineWidth) {
                lineWidth = 0;
                measuredHeight += maxLineHeight;
                maxLineHeight = 0;
            }
            lineWidth += deltaWidth;
            maxLineHeight = Math.max(maxLineHeight, child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
        }

        setMeasuredDimension(parentWidth,
                (measuredHeight > parentHeight ? MEASURED_STATE_TOO_SMALL : 0) | measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

    }
}
