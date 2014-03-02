package com.todddavies.components.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 *
 */
@SuppressWarnings("unused")
public class ProgressWheelView extends ProgressBar {

    public ProgressWheelView(Context context) {
        this(context, null);
    }

    public ProgressWheelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressWheelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        parseAttributes(context.obtainStyledAttributes(attrs, R.styleable.ProgressWheel));
    }

    private ProgressWheelDrawable checkIndeterminateDrawable() {
        Drawable ret = getIndeterminateDrawable();
        if (ret == null || !(ret instanceof ProgressWheelDrawable))
            throw new RuntimeException("The drawable is not a SmoothProgressDrawable");
        return (ProgressWheelDrawable) ret;
    }

    /**
     * Parse the attributes passed to the view from the XML
     *
     * @param a the attributes to parse
     */
    private void parseAttributes(TypedArray a) {
        ProgressWheelDrawable.Builder builder = new ProgressWheelDrawable.Builder();
        if (a.hasValue(R.styleable.ProgressWheel_barWidth)) {
            builder.setBarWidth((int) a.getDimension(R.styleable.ProgressWheel_barWidth, 0));
        }
        if (a.hasValue(R.styleable.ProgressWheel_rimWidth)) {
            builder.setRimWidth((int) a.getDimension(R.styleable.ProgressWheel_rimWidth, 0));
        }
        if (a.hasValue(R.styleable.ProgressWheel_spinSpeed)) {
            builder.setSpinSpeed((int) a.getDimension(R.styleable.ProgressWheel_spinSpeed, 0));
        }
        if (a.hasValue(R.styleable.ProgressWheel_barColor)) {
            builder.setBarColor(a.getColor(R.styleable.ProgressWheel_barColor, 0));
        }
        if (a.hasValue(R.styleable.ProgressWheel_barLength)) {
            builder.setBarLength((int) a.getDimension(R.styleable.ProgressWheel_barLength, 0));
        }
        if (a.hasValue(R.styleable.ProgressWheel_textSize)) {
            builder.setTextSize((int) a.getDimension(R.styleable.ProgressWheel_textSize, 0));
        }
        if (a.hasValue(R.styleable.ProgressWheel_textColor)) {
            builder.setTextColor(a.getColor(R.styleable.ProgressWheel_textColor, 0));
        }
        if (a.hasValue(R.styleable.ProgressWheel_text)) {
            setText(a.getString(R.styleable.ProgressWheel_text));
        }
        if (a.hasValue(R.styleable.ProgressWheel_rimColor)) {
            builder.setRimColor(a.getColor(R.styleable.ProgressWheel_rimColor, 0));
        }
        if (a.hasValue(R.styleable.ProgressWheel_circleColor)) {
            builder.setCircleColor(a.getColor(R.styleable.ProgressWheel_circleColor, 0));
        }
        if (a.hasValue(R.styleable.ProgressWheel_contourColor)) {
            builder.setContourColor(a.getColor(R.styleable.ProgressWheel_contourColor, 0));
        }
        if (a.hasValue(R.styleable.ProgressWheel_contourSize)) {
            builder.setContourSize((int) a.getDimension(R.styleable.ProgressWheel_contourSize, 0));
        }
        setIndeterminateDrawable(builder.build());
        // Recycle
        a.recycle();
    }

    /**
     * Set the text in the progress bar
     * Doesn't invalidate the view
     *
     * @param text the text to show ('\n' constitutes a new line)
     */
    public void setText(String text) {
        checkIndeterminateDrawable().setText(text);
    }

    public void setFullRadius(int fullRadius) {
        checkIndeterminateDrawable().setFullRadius(fullRadius);
    }

    public void setCircleRadius(int circleRadius) {
        checkIndeterminateDrawable().setCircleRadius(circleRadius);
    }

    public void setBarLength(int barLength) {
        checkIndeterminateDrawable().setBarLength(barLength);
    }

    public void setBarWidth(int barWidth) {
        checkIndeterminateDrawable().setBarWidth(barWidth);
    }

    public void setRimWidth(int rimWidth) {
        checkIndeterminateDrawable().setRimWidth(rimWidth);
    }

    public void setTextSize(int textSize) {
        checkIndeterminateDrawable().setTextSize(textSize);
    }

    public void setContourSize(float contourSize) {
        checkIndeterminateDrawable().setContourSize(contourSize);
    }

    public void setBarColor(int barColor) {
        checkIndeterminateDrawable().setBarColor(barColor);
    }

    public void setContourColor(int contourColor) {
        checkIndeterminateDrawable().setContourColor(contourColor);
    }

    public void setCircleColor(int circleColor) {
        checkIndeterminateDrawable().setCircleColor(circleColor);
    }

    public void setRimColor(int rimColor) {
        checkIndeterminateDrawable().setRimColor(rimColor);
    }

    public void setTextColor(int textColor) {
        checkIndeterminateDrawable().setTextColor(textColor);
    }

    public void setSpinSpeed(int spinSpeed) {
        checkIndeterminateDrawable().setSpinSpeed(spinSpeed);
    }

    public int getFullRadius() {
        return checkIndeterminateDrawable().getFullRadius();
    }

    public int getCircleRadius() {
        return checkIndeterminateDrawable().getCircleRadius();
    }

    public int getBarLength() {
        return checkIndeterminateDrawable().getBarLength();
    }

    public int getBarWidth() {
        return checkIndeterminateDrawable().getBarWidth();
    }

    public int getRimWidth() {
        return checkIndeterminateDrawable().getRimWidth();
    }

    public int getTextSize() {
        return checkIndeterminateDrawable().getTextSize();
    }

    public float getContourSize() {
        return checkIndeterminateDrawable().getContourSize();
    }

    public int getBarColor() {
        return checkIndeterminateDrawable().getBarColor();
    }

    public int getContourColor() {
        return checkIndeterminateDrawable().getContourColor();
    }

    public int getCircleColor() {
        return checkIndeterminateDrawable().getCircleColor();
    }

    public int getRimColor() {
        return checkIndeterminateDrawable().getRimColor();
    }

    public int getTextColor() {
        return checkIndeterminateDrawable().getTextColor();
    }

    public int getSpinSpeed() {
        return checkIndeterminateDrawable().getSpinSpeed();
    }

    public boolean isSpinning() {
        return checkIndeterminateDrawable().isSpinning();
    }

    public String getText() {
        return checkIndeterminateDrawable().getText();
    }
}
