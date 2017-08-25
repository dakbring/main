package com.demo.dakbring.maindemo.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.demo.dakbring.maindemo.R;

public class ColorChangeableIcon extends AppCompatImageView {

    private ColorStateList colorStateList;

    private int color;

    public ColorChangeableIcon(Context context) {
        super(context);
    }

    public ColorChangeableIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ColorChangeableIcon(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ColorChangeableIcon, defStyle, 0);
        colorStateList = a.getColorStateList(R.styleable.ColorChangeableIcon_colorSelector);
        color = a.getColor(R.styleable.ColorChangeableIcon_changeableColor, 0);

        updateColor();
        a.recycle();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        updateColor();
    }

    private void updateColor() {
        int value = 0;
        if (colorStateList != null && colorStateList.isStateful()) {
            value = colorStateList.getColorForState(getDrawableState(), 0);
        }
        if (color != 0) {
            value = color;
        }
        setColorFilter(value);
    }
}
