package com.wc.pagerbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * 选择器
 * Created by RushKing on 2017/2/16.
 */

public class SelectView extends View {
    //左边右边矩形画笔，左边右边字体画笔
    private Paint pLeft, pRight, textLeft, textRight;
    private RectF rectFLeft, rectFRight;
    private Path pathLeft, pathRight;
    private float textSize = 14;//默认字体大小
    private int radius = 6;//默认圆角大小
    //Path的圆角位置 two radius values [X, Y]. The corners are ordered top-left, top-right, bottom-right, bottom-left
    private float[] radiiLeft = {radius, radius, 0f, 0f, 0f, 0f, radius, radius};
    private float[] radiiRight = {0f, 0f, radius, radius, radius, radius, 0f, 0f};
    //默认颜色
    private int selectNoColor = Color.WHITE;
    private int selectedColor = Color.parseColor("#212121");
    //控件宽高
    private int width, height;
    //左边右边文字
    private String leftString, rightString;
    //点击控制参数
    private boolean isRight;
    private boolean action_dwon;
    private float action_x;

    //回调接口
    private OnSelectListener mOnSelectListener;

    //回调接口
    public interface OnSelectListener {
        void onSelect(SelectView view, boolean isLeft);
    }

    public SelectView(Context context) {
        super(context);
        init();
    }

    public SelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //初始化画笔
    private void init() {
        pathLeft = new Path();
        pathRight = new Path();

        pLeft = new Paint();
        pLeft.setDither(true);
        pLeft.setAntiAlias(true);
        pRight = new Paint();
        pRight.setDither(true);
        pRight.setAntiAlias(true);

        textLeft = new Paint();
        textLeft.setDither(true);
        textLeft.setAntiAlias(true);
        textLeft.setTextSize(getRawSize(TypedValue.COMPLEX_UNIT_SP, textSize));
        textLeft.setTextAlign(Paint.Align.CENTER);
        textRight = new Paint();
        textRight.setDither(true);
        textRight.setAntiAlias(true);
        textRight.setTextSize(getRawSize(TypedValue.COMPLEX_UNIT_SP, textSize));
        textRight.setTextAlign(Paint.Align.CENTER);

        rectFLeft = new RectF();
        rectFRight = new RectF();
    }

    //设置回调监听
    public void setOnSelectListener(OnSelectListener l) {
        mOnSelectListener = l;
    }

    //设置未选中颜色
    public void setColor(int color) {
        this.selectNoColor = color;
    }

    //设置选中颜色
    public void setSelectedColor(int color) {
        this.selectedColor = color;
    }

    //设置圆角大小
    public void setRadius(int radius) {
        this.radius = radius;
        //Path的圆角位置 two radius values [X, Y]. The corners are ordered top-left, top-right, bottom-right, bottom-left
        radiiLeft = new float[]{this.radius, this.radius, 0f, 0f, 0f, 0f, this.radius, this.radius};
        radiiRight = new float[]{0f, 0f, this.radius, this.radius, this.radius, this.radius, 0f, 0f};
    }

    //设置字体大小，默认14sp
    public void setTextSize(int size) {
        this.textSize = size;
        textLeft.setTextSize(getRawSize(TypedValue.COMPLEX_UNIT_SP, this.textSize));
        textRight.setTextSize(getRawSize(TypedValue.COMPLEX_UNIT_SP, this.textSize));
    }

    //设置左边右边文字
    public void setText(String left, String right) {
        leftString = left;
        rightString = right;
        invalidate();
    }

    //设置选择左边
    public void setSelect(boolean left) {
        isRight = !left;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (TextUtils.isEmpty(leftString) || TextUtils.isEmpty(rightString)) {
            return;
        }
        if (!isRight) {// 绘制左边状态
            canvas.save();

            pLeft.setColor(selectNoColor);
            pLeft.setStyle(Paint.Style.FILL);

            pRight.setColor(selectNoColor);
            pRight.setStrokeWidth(2.0f);
            pRight.setStyle(Paint.Style.STROKE);

            textLeft.setColor(selectedColor);
            textRight.setColor(selectNoColor);

            rectFLeft.left = 0;
            rectFLeft.top = 0;
            rectFLeft.bottom = height;
            rectFLeft.right = width / 2;

            pathLeft.addRoundRect(rectFLeft, radiiLeft, Path.Direction.CW);
            canvas.drawPath(pathLeft, pLeft);
            pathLeft.reset();

            rectFRight.left = width / 2;
            rectFRight.top = 0;
            rectFRight.bottom = height;
            rectFRight.right = width;

            pathRight.addRoundRect(rectFRight, radiiRight, Path.Direction.CW);
            canvas.drawPath(pathRight, pRight);
            pathRight.reset();

            FontMetrics fm = textLeft.getFontMetrics();
//            float textHight = (float) Math.ceil(fm.descent - fm.ascent);
//            float y = height / 2 + textHight / 2;
//            float x = width * 0.25f - textLeft.measureText(leftString) * 0.5f;
            float y = rectFLeft.centerY() - fm.top / 2 - fm.bottom / 2;
            float x = rectFLeft.centerX();
            canvas.drawText(leftString, x, y, textLeft);

            fm = textRight.getFontMetrics();
//            textHight = (float) Math.ceil(fm.descent - fm.ascent);
//            y = height / 2 + textHight / 2 - 10;
//            float xr = width * 0.75f - textRight.measureText(rightString) * 0.5f;
            y = rectFRight.centerY() - fm.top / 2 - fm.bottom / 2;
            float xr = rectFRight.centerX();
            canvas.drawText(rightString, xr, y, textRight);

            canvas.restore();
        } else {
            canvas.save();

            pLeft.setColor(selectNoColor);
            pLeft.setStyle(Paint.Style.STROKE);
            pLeft.setStrokeWidth(2.0f);

            pRight.setColor(selectNoColor);
            pRight.setStyle(Paint.Style.FILL);

            textLeft.setColor(selectNoColor);
            textRight.setColor(selectedColor);

            rectFLeft.left = 0;
            rectFLeft.top = 0;
            rectFLeft.bottom = height;
            rectFLeft.right = width / 2;

            pathLeft.addRoundRect(rectFLeft, radiiLeft, Path.Direction.CW);
            canvas.drawPath(pathLeft, pLeft);
            pathLeft.reset();

            rectFRight.left = width / 2;
            rectFRight.top = 0;
            rectFRight.bottom = height;
            rectFRight.right = width;

            pathRight.addRoundRect(rectFRight, radiiRight, Path.Direction.CW);
            canvas.drawPath(pathRight, pRight);
            pathRight.reset();

            FontMetrics fm = textLeft.getFontMetrics();
            float y = rectFLeft.centerY() - fm.top / 2 - fm.bottom / 2;
            float x = rectFLeft.centerX();
            canvas.drawText(leftString, x, y, textLeft);

            fm = textRight.getFontMetrics();
            y = rectFRight.centerY() - fm.top / 2 - fm.bottom / 2;
            float xr = rectFRight.centerX();
            canvas.drawText(rightString, xr, y, textRight);

            canvas.restore();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                action_dwon = true;
                action_x = event.getRawX();
                Log.v("onTouchEvent", "按下");
                return true;
            case MotionEvent.ACTION_UP:
                Log.v("onTouchEvent", "松开");
                onCkick(event);
                break;
            case MotionEvent.ACTION_CANCEL:
                onCkick(event);
                break;

        }
        return super.onTouchEvent(event);
    }

    // 点击事件处理
    private void onCkick(MotionEvent event) {
        if (action_dwon) {
//			Log.v("onTouchEvent", "getRawX()=" + event.getRawX());
            float middle = getLeft() + width / 2f;
            if (action_x > middle && event.getRawX() > middle) {// 点击的右边,松开的右边
//				Log.v("onTouchEvent", "点击的右边,松开的右边");
                if (!isRight) {
                    isRight = true;
                    if (mOnSelectListener != null) {
                        mOnSelectListener.onSelect(this, !isRight);
                    }
//					invalidate();
                }
            } else if (action_x < middle && event.getRawX() < middle) {// 点击的左边,松开的左边
//				Log.v("onTouchEvent", "点击的左边,松开的左边");
                if (isRight) {
                    isRight = false;
                    if (mOnSelectListener != null) {
                        mOnSelectListener.onSelect(this, !isRight);
                    }
//					invalidate();
                }
            }

            action_dwon = false;
            action_x = 0;
        }
    }

    private float getRawSize(int unit, float size) {
        Context context = getContext();
        Resources resources;
        if (context == null) {
            resources = Resources.getSystem();
        } else {
            resources = context.getResources();
        }
        return TypedValue.applyDimension(unit, size, resources.getDisplayMetrics());
    }
}
