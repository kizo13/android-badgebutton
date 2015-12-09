package com.github.kizo13.badgebutton;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;

/**
 * Created by Kilvinger Zoltán on 2015.12.09..
 */
public class BadgeButton extends ImageButton {

    private static final String TAG = "BadgeButton";

    // Constants
    private static final int BADGESHAPE_CIRCLE = 0;
    private static final int BADGESHAPE_ROUNDEDRECT = 1;
    private static final int BADGEPOSITION_LEFTTOP = 0;
    private static final int BADGEPOSITION_TOPCENTER = 1;
    private static final int BADGEPOSITION_RIGHTTOP = 2;
    private static final int BADGEPOSITION_LEFTCENTER = 3;
    private static final int BADGEPOSITION_CENTER = 4;
    private static final int BADGEPOSITION_RIGHTCENTER = 5;
    private static final int BADGEPOSITION_LEFTBOTTOM = 6;
    private static final int BADGEPOSITION_BOTTOMCENTER = 7;
    private static final int BADGEPOSITION_RIGHTBOTTOM = 8;
    private static final int BADGEALIGN_INSIDE = 0;
    private static final int BADGEALIGN_OUTSIDE = 1;

    // Attrs
    private int badgeSize = 150;
    private int badgeColor = Color.parseColor("#DA4F49");
    private int badgeTextColor = Color.parseColor("#FFFFFF");
    private int badgeShape = 0;
    private int badgeShapeRadius = badgeSize / 10;
    private int badgePadding = badgeSize / 4;
    private int badgePosition = 2;  //rightTop
    private int badgeAlign = 0;  //inside

    private int value = 0;
    private int mWidth, mHeight;
    private float badgeTextSize = (float)(badgeSize * 0.1) * getResources().getDisplayMetrics().density;
    private int bCenterX = 0;
    private int bCenterY = 0;
    private int bPadding = 0;
    private int bLeft = 0;
    private int bTop = 0;
    private int bRight = 0;
    private int bBottom = 0;
    private Paint badgePaint, badgeTextPaint;

    public BadgeButton(Context context) {
        super(context);
        init();
    }

    public BadgeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BadgeButton, 0, 0);

        try {
            badgeSize = a.getInteger(R.styleable.BadgeButton_badgeSize, badgeSize);
            badgeColor = a.getColor(R.styleable.BadgeButton_badgeColor, badgeColor);
            badgeTextColor = a.getColor(R.styleable.BadgeButton_badgeTextColor, badgeTextColor);
            badgeShape = a.getInteger(R.styleable.BadgeButton_badgeShape, badgeShape);
            badgeShapeRadius = a.getInteger(R.styleable.BadgeButton_badgeShapeRadius, badgeShapeRadius);
            badgePadding = a.getInteger(R.styleable.BadgeButton_badgePadding, badgePadding);
            badgePosition = a.getInteger(R.styleable.BadgeButton_badgePosition, badgePosition);
            badgeAlign = a.getInteger(R.styleable.BadgeButton_badgeAlign, badgeAlign);
        } finally {
            a.recycle();
        }

        init();
    }

    private void init() {

        badgePaint = new Paint();
        badgePaint.setColor(badgeColor);
        badgePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        badgeTextPaint = new Paint();
        badgeTextPaint.setColor(badgeTextColor);
        badgeTextPaint.setTextAlign(Paint.Align.CENTER);
        badgeTextPaint.setTextSize(badgeTextSize);
        badgeTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if((getLayoutParams().width == WindowManager.LayoutParams.MATCH_PARENT)
                || (getLayoutParams().width == WindowManager.LayoutParams.FILL_PARENT)) {
            mWidth = MeasureSpec.getSize(widthMeasureSpec);
        } else mWidth = getLayoutParams().width;

        if((getLayoutParams().height == WindowManager.LayoutParams.MATCH_PARENT)
                ||(getLayoutParams().height == WindowManager.LayoutParams.FILL_PARENT)) {
            mHeight = MeasureSpec.getSize(heightMeasureSpec);
        } else mHeight = getLayoutParams().height;

        setMeasuredDimension(mWidth, mHeight);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Allow to draw outside the View
        ViewGroup parentView = (ViewGroup)BadgeButton.this.getParent();
        parentView.setClipChildren(false);
        parentView.setClipToPadding(false);

        if (value != 0) {
            switch (badgeAlign) {
                case BADGEALIGN_OUTSIDE:
                    bPadding = 0;
                    break;
                case BADGEALIGN_INSIDE:
                    bPadding = badgePadding + (badgeSize / 2);
                    break;
            }

            switch (badgePosition) {
                case BADGEPOSITION_LEFTTOP:
                    bCenterX = getPaddingLeft() + bPadding;
                    bCenterY = getPaddingTop() + bPadding;
                    break;
                case BADGEPOSITION_TOPCENTER:
                    bCenterX = mWidth / 2;
                    bCenterY = getPaddingTop();
                    break;
                case BADGEPOSITION_RIGHTTOP:
                    bCenterX = mWidth - getPaddingRight() - bPadding;
                    bCenterY = getPaddingTop() + bPadding;
                    break;
                case BADGEPOSITION_LEFTCENTER:
                    bCenterX = getPaddingLeft() + bPadding;
                    bCenterY = mHeight / 2;
                    break;
                case BADGEPOSITION_CENTER:
                    bCenterX = mWidth / 2;
                    bCenterY = mHeight / 2;
                    break;
                case BADGEPOSITION_RIGHTCENTER:
                    bCenterX = mWidth - getPaddingRight() - bPadding;
                    bCenterY = mHeight / 2;
                    break;
                case BADGEPOSITION_LEFTBOTTOM:
                    bCenterX = getPaddingLeft() + bPadding;
                    bCenterY = mHeight - getPaddingBottom() - bPadding;
                    break;
                case BADGEPOSITION_BOTTOMCENTER:
                    bCenterX = mWidth / 2;
                    bCenterY = mHeight - getPaddingBottom() - bPadding;
                    break;
                case BADGEPOSITION_RIGHTBOTTOM:
                    bCenterX = mWidth - getPaddingRight() - bPadding;
                    bCenterY = mHeight - getPaddingBottom() - bPadding;
                    break;
            }

            bLeft = bCenterX - (badgeSize / 2);
            bTop = bCenterY - (badgeSize / 2);
            bRight = bCenterX + (badgeSize / 2);
            bBottom = bCenterY + (badgeSize / 2);

            if (badgeShape == BADGESHAPE_CIRCLE) {
                canvas.drawCircle((float)bCenterX, (float)bCenterY, (float)(badgeSize / 2), badgePaint);
            } else if (badgeShape == BADGESHAPE_ROUNDEDRECT) {
                canvas.drawRoundRect((float)bLeft, (float)bTop, (float)bRight, (float)bBottom,
                        badgeShapeRadius, badgeShapeRadius, badgePaint);
            }

            int space = (int) (badgeSize-badgeTextSize)/2;

            canvas.drawText(String.valueOf(value),
                    (float) bCenterX,
                    (float) (bBottom - space - ((badgeTextSize / getResources().getDisplayMetrics().density) / 2)),
                    badgeTextPaint);
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
