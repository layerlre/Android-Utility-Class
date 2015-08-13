package com.layer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Created by layer on 2/25/2015.
 */
public class TabTextView extends TextView {
    private boolean selected;
    private int underlineHeight = 2;
    private Paint rectPaint;
    private int underlineColor = 0xfff15a24;

    public TabTextView(Context context) {
        super(context);
        init();
    }

    public TabTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){

        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setStyle(Paint.Style.FILL);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        underlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, underlineHeight, dm);
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
        if (selected) {
            setTextColor(underlineColor);
        }else {
            setTextColor(0xff000000);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (selected){
            final int height = getHeight();
            final int width = getWidth();
            rectPaint.setColor(underlineColor);
            canvas.drawRect(0, height - underlineHeight, width, height, rectPaint);
        }
    }
}
