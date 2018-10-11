package com.staplex.tfsht2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

class MetroViewGroup extends ViewGroup {

    public MetroViewGroup(Context context) {
        this(context, null);
    }

    public MetroViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MetroViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MetroViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(super.generateDefaultLayoutParams());
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        final int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        final int lineWidth = parentWidth - getPaddingLeft() - getPaddingRight();
        final int childHeight = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();

        // Measure result
        int measuredHeight = getPaddingTop() + getPaddingBottom();
        int currentLineWidth = 0;
        // Max child height in current line
        int maxLineHeight = 0;
        int measureState = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(MeasureSpec.makeMeasureSpec(lineWidth, MeasureSpec.AT_MOST),
                    MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.AT_MOST));
            measureState = combineMeasuredStates(measureState, child.getMeasuredState());
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            int deltaWidth = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            if (deltaWidth + currentLineWidth > lineWidth) {
                currentLineWidth = 0;
                measuredHeight += maxLineHeight;
                maxLineHeight = 0;
            }
            currentLineWidth += deltaWidth;
            maxLineHeight = Math.max(maxLineHeight, child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
        }

        setMeasuredDimension(resolveSizeAndState(parentWidth, widthMeasureSpec, measureState),
                resolveSizeAndState((measuredHeight > parentHeight ? MEASURED_STATE_TOO_SMALL : 0) | measuredHeight,
                        heightMeasureSpec, measureState));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int lineWidth = right - left - getPaddingLeft() - getPaddingRight();
        int currentHeight = getPaddingTop() + getPaddingBottom();

        int currentLineWidth = 0;
        int maxLineHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            int deltaWidth = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            if (deltaWidth + currentLineWidth > lineWidth) {
                currentLineWidth = 0;
                currentHeight += maxLineHeight;
                maxLineHeight = 0;
            }
            currentLineWidth += deltaWidth;
            int x = getPaddingLeft() + lineWidth - currentLineWidth + layoutParams.leftMargin;
            int y = currentHeight + layoutParams.topMargin;
            child.layout(x, y, x + child.getMeasuredWidth(), y + child.getMeasuredHeight());
            maxLineHeight = Math.max(maxLineHeight, child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
        }
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }

}
