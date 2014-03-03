package com.todddavies.components.progressbar;

import android.graphics.*;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.text.TextUtils;

/**
 *
 */
@SuppressWarnings("unused")
public class ProgressWheelDrawable extends Drawable implements Animatable {

    static final String TAG = "ProgressWheelDrawable";

    private static final long FRAME_DURATION = 1000 / 60;
    private final static float OFFSET_PER_FRAME = 0.01f;

    //Sizes (with defaults)
    private int layout_height;
    private int layout_width;
    private int fullRadius;
    private int circleRadius;
    private int barLength;
    private int barWidth;
    private int rimWidth;
    private int textSize;
    private float contourSize;

    //Padding (with defaults)
    private int paddingTop = 5;
    private int paddingBottom = 5;
    private int paddingLeft = 5;
    private int paddingRight = 5;

    //Colors (with defaults)
    private int barColor;
    private int contourColor;
    private int circleColor;
    private int rimColor;
    private int textColor;

    //Paints
    private Paint barPaint = new Paint();
    private Paint circlePaint = new Paint();
    private Paint rimPaint = new Paint();
    private Paint textPaint = new Paint();
    private Paint contourPaint = new Paint();

    //Rectangles
    @SuppressWarnings("unused")
    private RectF rectBounds = new RectF();
    private RectF circleBounds = new RectF();
    private RectF circleOuterContour = new RectF();
    private RectF circleInnerContour = new RectF();

    // Animation
    // The amount of pixels to move the bar by on each draw
    private int spinSpeed;
    int progress = 0;
    boolean isSpinning = false;

    //Other
    private String text = "";
    private String[] splitText = {};

    Runnable mUpdater = new Runnable() {
        @Override
        public void run() {
            if (isSpinning) {
                progress += spinSpeed;
                if (progress > 360) {
                    progress = 0;
                }
                doStart();
            }
        }
    };

    private ProgressWheelDrawable(ProgressWheelDrawable.Builder builder) {
        setText(builder.text);
        this.textColor = builder.textColor;
        this.textSize = builder.textSize;
        this.barColor = builder.barColor;
        this.rimColor = builder.rimColor;
        this.rimWidth = builder.rimWidth;
        this.spinSpeed = builder.spinSpeed;
        this.circleColor = builder.circleColor;
        this.barWidth = builder.barWidth;
        this.barLength = builder.barLength;
        this.contourColor = builder.contourColor;
        this.contourSize = builder.contourSize;
        this.circleRadius = builder.circleRadius;
        this.fullRadius = builder.fullRadius;
    }

    @Override
    public void start() {
        if (isRunning()) {
            return;
        }
        doStart();
    }

    void doStart() {
        scheduleSelf(mUpdater, SystemClock.currentThreadTimeMillis() + FRAME_DURATION);
        invalidateSelf();
    }

    @Override
    public void stop() {
        if (!isRunning()) {
            return;
        }
        isSpinning = false;
        unscheduleSelf(mUpdater);
    }

    @Override
    public void scheduleSelf(Runnable what, long when) {
        isSpinning = true;
        super.scheduleSelf(what, when);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);

        // Share the dimensions
        layout_width = bounds.width();
        layout_height = bounds.height();

        setupBounds();
        setupPaints();
        invalidateSelf();
    }

    @Override
    public boolean isRunning() {
        return isSpinning;
    }

    /**
     * Set the bounds of the component
     */
    private void setupBounds() {
        // Width should equal to Height, find the min value to steup the circle
        int minValue = Math.min(layout_width, layout_height);

        // Calc the Offset if needed
        int xOffset = layout_width - minValue;
        int yOffset = layout_height - minValue;

        // Add the offset
        paddingTop = (yOffset / 2);
        paddingBottom = (yOffset / 2);
        paddingLeft = (xOffset / 2);
        paddingRight = (xOffset / 2);

        rectBounds = new RectF(paddingLeft,
                paddingTop,
                getBounds().width() - paddingRight,
                getBounds().height() - paddingBottom);

        circleBounds = new RectF(paddingLeft + barWidth,
                paddingTop + barWidth,
                getBounds().width() - paddingRight - barWidth,
                getBounds().height() - paddingBottom - barWidth);
        circleInnerContour = new RectF(circleBounds.left + (rimWidth / 2.0f) + (contourSize / 2.0f), circleBounds.top + (rimWidth / 2.0f) + (contourSize / 2.0f), circleBounds.right - (rimWidth / 2.0f) - (contourSize / 2.0f), circleBounds.bottom - (rimWidth / 2.0f) - (contourSize / 2.0f));
        circleOuterContour = new RectF(circleBounds.left - (rimWidth / 2.0f) - (contourSize / 2.0f), circleBounds.top - (rimWidth / 2.0f) - (contourSize / 2.0f), circleBounds.right + (rimWidth / 2.0f) + (contourSize / 2.0f), circleBounds.bottom + (rimWidth / 2.0f) + (contourSize / 2.0f));

        fullRadius = (this.getBounds().width() - paddingRight - barWidth) / 2;
        circleRadius = (fullRadius - barWidth) + 1;
    }

    /**
     * Set the properties of the paints we're using to
     * draw the progress wheel
     */
    private void setupPaints() {
        barPaint.setColor(barColor);
        barPaint.setAntiAlias(true);
        barPaint.setStyle(Paint.Style.STROKE);
        barPaint.setStrokeWidth(barWidth);

        rimPaint.setColor(rimColor);
        rimPaint.setAntiAlias(true);
        rimPaint.setStyle(Paint.Style.STROKE);
        rimPaint.setStrokeWidth(rimWidth);

        circlePaint.setColor(circleColor);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL);

        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);

        contourPaint.setColor(contourColor);
        contourPaint.setAntiAlias(true);
        contourPaint.setStyle(Paint.Style.STROKE);
        contourPaint.setStrokeWidth(contourSize);
    }

    @Override
    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        //Draw the rim
        canvas.drawArc(circleBounds, 360, 360, false, rimPaint);
        canvas.drawArc(circleOuterContour, 360, 360, false, contourPaint);
        canvas.drawArc(circleInnerContour, 360, 360, false, contourPaint);
        //Draw the bar
        if (isSpinning) {
            canvas.drawArc(circleBounds, progress - 90, barLength, false, barPaint);
        } else {
            canvas.drawArc(circleBounds, -90, progress, false, barPaint);
        }
        //Draw the inner circle
        canvas.drawCircle((circleBounds.width() / 2) + rimWidth + paddingLeft,
                (circleBounds.height() / 2) + rimWidth + paddingTop,
                circleRadius,
                circlePaint);
        //Draw the text (attempts to center it horizontally and vertically)
        float textHeight = textPaint.descent() - textPaint.ascent();
        float verticalTextOffset = (textHeight / 2) - textPaint.descent();

        for (String s : splitText) {
            float horizontalTextOffset = textPaint.measureText(s) / 2;
            canvas.drawText(s, getBounds().width() / 2 - horizontalTextOffset,
                    getBounds().height() / 2 + verticalTextOffset, textPaint);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        barPaint.setAlpha(alpha);
        circlePaint.setAlpha(alpha);
        rimPaint.setAlpha(alpha);
        textPaint.setAlpha(alpha);
        contourPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        barPaint.setColorFilter(cf);
        circlePaint.setColorFilter(cf);
        rimPaint.setColorFilter(cf);
        textPaint.setColorFilter(cf);
        contourPaint.setColorFilter(cf);
    }

    /**
     * Set the text in the progress bar
     * Doesn't invalidate the view
     *
     * @param text the text to show ('\n' constitutes a new line)
     */
    public void setText(String text) {
        this.text = text;
        if (TextUtils.isEmpty(text)) {
            splitText = new String[0];
        } else {
            splitText = this.text.split("\n");
        }
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSPARENT;
    }

    public void setFullRadius(int fullRadius) {
        this.fullRadius = fullRadius;
        invalidateSelf();
    }

    public void setCircleRadius(int circleRadius) {
        this.circleRadius = circleRadius;
        invalidateSelf();
    }

    public void setBarLength(int barLength) {
        this.barLength = barLength;
        invalidateSelf();
    }

    public void setBarWidth(int barWidth) {
        this.barWidth = barWidth;
        invalidateSelf();
    }

    public void setRimWidth(int rimWidth) {
        this.rimWidth = rimWidth;
        invalidateSelf();
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        invalidateSelf();
    }

    public void setContourSize(float contourSize) {
        this.contourSize = contourSize;
        invalidateSelf();
    }

    public void setBarColor(int barColor) {
        this.barColor = barColor;
        invalidateSelf();
    }

    public void setContourColor(int contourColor) {
        this.contourColor = contourColor;
        invalidateSelf();
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
        invalidateSelf();
    }

    public void setRimColor(int rimColor) {
        this.rimColor = rimColor;
        invalidateSelf();
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        invalidateSelf();
    }

    public void setSpinSpeed(int spinSpeed) {
        this.spinSpeed = spinSpeed;
        invalidateSelf();
    }

    public int getFullRadius() {
        return fullRadius;
    }

    public int getCircleRadius() {
        return circleRadius;
    }

    public int getBarLength() {
        return barLength;
    }

    public int getBarWidth() {
        return barWidth;
    }

    public int getRimWidth() {
        return rimWidth;
    }

    public int getTextSize() {
        return textSize;
    }

    public float getContourSize() {
        return contourSize;
    }

    public int getBarColor() {
        return barColor;
    }

    public int getContourColor() {
        return contourColor;
    }

    public int getCircleColor() {
        return circleColor;
    }

    public int getRimColor() {
        return rimColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getSpinSpeed() {
        return spinSpeed;
    }

    public boolean isSpinning() {
        return isSpinning;
    }

    public String getText() {
        return text;
    }

    /**
     * Builder contains also default values.
     */
    public static class Builder {

        String text;
        int textSize = 20;
        int textColor = Color.BLACK;
        int circleColor = 0x00000000;
        int barLength = 20;
        int barColor = Color.RED;
        int barWidth = 20;
        int rimColor = 0xAADDDDDD;
        int rimWidth = 20;
        int spinSpeed = 2;
        int contourColor = Color.TRANSPARENT;
        int contourSize = 0;
        int fullRadius = 100;
        int circleRadius = 80;

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public Builder setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder setCircleColor(int circleColor) {
            this.circleColor = circleColor;
            return this;
        }

        public Builder setBarLength(int barLength) {
            this.barLength = barLength;
            return this;
        }

        public Builder setBarColor(int barColor) {
            this.barColor = barColor;
            return this;
        }

        public Builder setBarWidth(int barWidth) {
            this.barWidth = barWidth;
            return this;
        }

        public Builder setRimColor(int rimColor) {
            this.rimColor = rimColor;
            return this;
        }

        public Builder setRimWidth(int rimWidth) {
            this.rimWidth = rimWidth;
            return this;
        }

        public Builder setSpinSpeed(int spinSpeed) {
            this.spinSpeed = spinSpeed;
            return this;
        }

        public Builder setFullRadius(int fullRadius) {
            this.fullRadius = fullRadius;
            return this;
        }

        public Builder setCircleRadius(int circleRadius) {
            this.circleRadius = circleRadius;
            return this;
        }

        public Builder setContourColor(int contourColor) {
            this.contourColor = contourColor;
            return this;
        }

        public Builder setContourSize(int contourSize) {
            this.contourSize = contourSize;
            return this;
        }

        public ProgressWheelDrawable build() {
            return new ProgressWheelDrawable(this);
        }
    }
}
