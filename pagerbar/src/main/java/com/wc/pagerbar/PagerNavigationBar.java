package com.wc.pagerbar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.HorizontalScrollView;

/**
 * ViewPager导航栏
 * Created by RushKing on 2017/4/13.
 */

public class PagerNavigationBar extends HorizontalScrollView {
    private final String TAG = "PagerNavigationBar";
    private PagerNavigationView mView;
    private ViewPager mViewPager;
    private int scrollOffset;
    private int screenWidth;
    private int mLayoutHeight;
    private int mCurItem = 0;   // Index of currently displayed page.
    private int mCount;
    private int lastScrollX;
    private float positionOffset;
    private int scrolledPosition;
    private OnNavigationBarClickListener mOnClickListener;

    /**
     * 导航栏点击回调监听
     */
    public interface OnNavigationBarClickListener {
        void onClickListener(int position);

        void onReClickListener(int position);
    }

    public PagerNavigationBar(Context context) {
        super(context);
        init();
    }

    public PagerNavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PagerNavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setFillViewport(true);
        setHorizontalScrollBarEnabled(false);
        mView = new PagerNavigationView(getContext());
        addView(mView);
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        scrollOffset = screenWidth / 3;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int speSize = MeasureSpec.getSize(widthMeasureSpec);
//        int speMode = MeasureSpec.getMode(widthMeasureSpec);
//        if (speMode == MeasureSpec.EXACTLY) {//MeasureSpec.EXACTLY是精确尺寸
//            mHeight = speSize;
//        }
        int speSize = MeasureSpec.getSize(heightMeasureSpec);
        int speMode = MeasureSpec.getMode(heightMeasureSpec);
        int minHeight = getSuggestedMinimumHeight();
        switch (speMode) {
            case MeasureSpec.UNSPECIFIED:
                mLayoutHeight = minHeight;
                break;
            case MeasureSpec.AT_MOST:
                mLayoutHeight = Math.min(speSize, minHeight);
                break;
            case MeasureSpec.EXACTLY:
            default:
                mLayoutHeight = mLayoutHeight == 0 ? speSize : Math.min(mLayoutHeight, speSize);
        }
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mLayoutHeight);
    }

    /**
     * 设置点击Tab回调监听
     */
    public void setOnNavigationBarClickListener(OnNavigationBarClickListener l) {
        mOnClickListener = l;
    }

    /**
     * 设置选中Text颜色
     */
    public void setSelectedTextColor(int color) {
        if (mView != null)
            mView.setSelectedTextColor(color);
    }

    /**
     * 设置未选中字体颜色
     */
    public void setTextColor(int color) {
        if (mView != null)
            mView.setTextColor(color);
    }

    /**
     * 设置字体大小
     */
    public void setTextSize(int size) {
        if (mView != null)
            mView.setTextSize(size);
    }

    /**
     * 设置跟滑线颜色
     */
    public void setIndicatorColor(int color) {
        if (mView != null)
            mView.setIndicatorColor(color);
    }

    /**
     * 设置跟滑线高度
     */
    public void setIndicatorHeight(int height) {
        if (mView != null)
            mView.setIndicatorHeight(height);
    }

    /**
     * 设置滑动方式，是否为默认方式
     */
    public void setIndicatorScrollModel(boolean isDefault) {
        if (mView != null)
            mView.setIndicatorScrollModel(isDefault);
    }

    /**
     * 设置最下边细线颜色
     */
    public void setLineColor(int color) {
        if (mView != null)
            mView.setLineColor(color);
    }

    /**
     * 设置最下边细线高度
     */
    public void setLineHeight(int height) {
        if (mView != null)
            mView.setLineHeight(height);
    }

    /***
     * 设置是否自动填充屏幕
     */
    public void setShouldExpand(boolean isExpand) {
        if (mView != null) {
            mView.setShouldExpand(isExpand);
        }
    }

    /**
     * 设置是否需要分割线
     */
    public void setDivider(boolean divider) {
        if (mView != null)
            mView.setDivider(divider);
    }

    /**
     * 设置分割线颜色
     */
    public void setDividerColor(int color) {
        if (mView != null)
            mView.setDividerColor(color);
    }

    /**
     * 设置分割线宽度
     */
    public void setDividerWidth(int width) {
        if (mView != null)
            mView.setDividerWidth(width);
    }

    /**
     * 设置分割线比例（占整个控件高度比）
     */
    public void setDividerWeight(int weight) {
        if (mView != null)
            mView.setDividerWeight(weight);
    }

    /**
     * 设置相隔空间
     */
    public void setSpacing(int spacing) {
        if (mView != null)
            mView.setSpacing(spacing);
    }

    /**
     * 设置当前选中哪一行
     */
    public void setCurrentItem(int item, boolean smoothScroll) {
        if (this.mViewPager != null) {
            this.mViewPager.setCurrentItem(item, smoothScroll);
            this.mCurItem = item;
        }
    }

    /**
     * 设置当前选中哪一行
     */
    public void setCurrentItem(int item) {
        if (this.mViewPager != null) {
            if (Math.abs(mCurItem - item) > 2) {
                this.mViewPager.setCurrentItem(item, false);
            } else {
                this.mViewPager.setCurrentItem(item);
            }
            this.mCurItem = item;
        }
    }

    public void setViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                PagerNavigationBar.this.positionOffset = positionOffset;
                PagerNavigationBar.this.scrolledPosition = position;
//                float dx = mPositionOffsetPixels - positionOffsetPixels;
//                isRight = dx > 0;
//                isLeft = dx < 0;
//                mPositionOffsetPixels = positionOffsetPixels;
                scrollToChild(position, (int) (positionOffset * mView.getPositionWidth(position)));
                mView.invalidate();
            }

            @Override
            public void onPageSelected(int position) {
                mCurItem = position;
//                Log.v(TAG, "position=" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        notifyDataSetChanged();
    }

    private void scrollToChild(int position, int offset) {
        if (mCount == 0) {
            return;
        }
        int newScrollX = mView.getPositionLeft(position) + offset;
        if (position > 0 || offset > 0) {
            newScrollX -= scrollOffset;
        }
        if (newScrollX != lastScrollX) {
            lastScrollX = newScrollX;
            scrollTo(newScrollX, 0);
        }
    }

    public void notifyDataSetChanged() {
        PagerAdapter adapter = mViewPager.getAdapter();
        if (mViewPager == null || adapter == null) {
            return;
        }
        // 获取数量
        mCount = adapter.getCount();
        String[] titles = new String[mCount];
        for (int i = 0; i < mCount; i++) {
            titles[i] = adapter.getPageTitle(i).toString();
        }
        mView.setTitles(titles);
    }

    private class PagerNavigationView extends View {
        private float mWidth;
        private float mHeight;
        private String[] titles;
        private int spacing = dip2px(16);
        private float mTextSize = sp2px(15);

        private int mTextColor = Color.BLACK;
        private int mSetSelectedTextColor = Color.RED;

        private int mIndicatorColor = Color.RED;
        private int mIndicatorHeight = dip2px(4);
        private int mLineColor = Color.parseColor("#DDDDDD");
        private int mLineHeight = 1;

        private boolean isExpand;
        private boolean hasDivider;
        private int mDividerColor = Color.parseColor("#DDDDDD");
        private int mDividerWidth = 1;
        private float mDividerWeight = 0.4f;

        private Paint mPaint, mLinePaint;
        private boolean isDefault = true;
        private int mTouchSlop;
        private boolean noscreen;//是否不满全屏并且需要全屏铺展

        public PagerNavigationView(Context context) {
            super(context);
            mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setTextSize(mTextSize);

            mLinePaint = new Paint();
            mLinePaint.setAntiAlias(true);
            mLinePaint.setStyle(Paint.Style.FILL);
        }

        public void setIndicatorScrollModel(boolean isDefault) {
            this.isDefault = isDefault;
        }

        public void setSpacing(int spacing) {
            this.spacing = dip2px(spacing);
        }

        public void setTextSize(int size) {
            mTextSize = sp2px(size);
        }

        public void setTextColor(int color) {
            mTextColor = color;
        }

        public void setSelectedTextColor(int color) {
            mSetSelectedTextColor = color;
        }

        public void setIndicatorColor(int color) {
            mIndicatorColor = color;
        }

        public void setIndicatorHeight(int height) {
            mIndicatorHeight = height;
        }

        public void setLineColor(int color) {
            mLineColor = color;
        }

        public void setLineHeight(int height) {
            mLineHeight = height;
        }

        public void setShouldExpand(boolean isExpand) {
            this.isExpand = isExpand;
        }

        public void setDivider(boolean divider) {
            hasDivider = divider;
        }

        public void setDividerColor(int color) {
            mDividerColor = color;
        }

        public void setDividerWidth(int width) {
            mDividerWidth = width;
        }

        public void setDividerWeight(int weight) {
            mDividerWeight = weight;
        }

        public int getPositionWidth(int position) {
            int width = 0;
            if (titles != null && titles.length > 0 && position < titles.length) {
                width = (int) mPaint.measureText(titles[position]) + spacing * 2;
            }
            return width;
        }

        public int getPositionLeft(int position) {
            float left = 0;
            if (titles != null && titles.length > 0 && position < titles.length) {
                for (int i = 0; i < titles.length; i++) {
                    if (position == i) {
                        return (int) left;
                    } else {
                        if (i != titles.length - 1) {
                            left = left + mPaint.measureText(titles[i]) + spacing * 2;
                        }
                    }
                }
            }
            return (int) left;
        }

        public void setTitles(String[] titles) {
            this.titles = titles;
            requestLayout();
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            mWidth = spacing;
            if (mPaint.getTextSize() != mTextSize) {
                mPaint.setTextSize(mTextSize);
            }
            if (titles != null && titles.length > 0) {
                for (int i = 0; i < titles.length; i++) {
                    if (i == titles.length - 1) {
                        mWidth = mWidth + mPaint.measureText(titles[i]) + spacing;
                    } else {
                        mWidth = mWidth + mPaint.measureText(titles[i]) + spacing * 2;
                    }

                }
            }
            mHeight = mLayoutHeight;
            if (isExpand) {
//                Log.v(TAG, "mWidth=" + mWidth + "\tmHeight" + mHeight + "\tscreenWidth=" + screenWidth);
                if (mWidth < screenWidth) {
                    mWidth = screenWidth;
                    noscreen = true;
                } else {
                    noscreen = false;
                }
            }
            setMeasuredDimension((int) mWidth, (int) mHeight);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float startX = spacing;
            //绘制Text以及分割线
            if (titles != null && titles.length > 0) {
                for (int i = 0; i < titles.length; i++) {
                    String s = titles[i];
                    Paint.FontMetrics fm = mPaint.getFontMetrics();
                    float textWidth = mPaint.measureText(s);
                    float x;
                    if (noscreen) {
                        float textFullWidth = mWidth / titles.length;
                        startX = textFullWidth * (i + 1) - (textFullWidth + textWidth) * 0.5f;
                        x = startX;
                    } else {
                        x = startX;
                        //数字文字
                        if (i == titles.length - 1) {
                            startX = startX + textWidth + spacing;
                        } else {
                            startX = startX + textWidth + spacing * 2;
                        }
                    }
                    float y = mHeight / 2 + (fm.descent - fm.ascent) / 2 - fm.descent;
                    if (mCurItem == i) {//选中的那一行
                        if (mPaint.getColor() != mSetSelectedTextColor) {
                            mPaint.setColor(mSetSelectedTextColor);
                        }
                    } else {
                        if (mPaint.getColor() != mTextColor) {
                            mPaint.setColor(mTextColor);
                        }
                    }
                    canvas.drawText(s, x, y, mPaint);
//                    Log.d(TAG, "drawText:" + s + "\tx=" + x + "\ty=" + y);
                    if (hasDivider && mDividerWidth != 0) {
                        if (mLinePaint.getColor() != mDividerColor) {
                            mLinePaint.setColor(mDividerColor);
                        }
                        mLinePaint.setStrokeWidth(mDividerWidth);
                        if (i != titles.length - 1) {
                            float dividerHeight = mHeight * mDividerWeight;
                            float dividerX;
                            if (noscreen) {
                                float textFullWidth = mWidth / titles.length;
                                dividerX = textFullWidth * (i + 1) - mDividerWidth * 0.5f;
                            } else {
                                dividerX = x + textWidth + spacing - mDividerWidth * 0.5f;
                            }
                            canvas.drawLine(dividerX, (mHeight - dividerHeight) * 0.5f, dividerX, (mHeight + dividerHeight) * 0.5f, mLinePaint);
                        }
                    }
                }
            }
            //绘制Indicator
            if (mIndicatorHeight != 0 && titles != null && titles.length > 0) {
                if (mLinePaint.getColor() != mIndicatorColor) {
                    mLinePaint.setColor(mIndicatorColor);
                }
                mLinePaint.setStrokeWidth(mIndicatorHeight);
                startX = spacing;
                for (int i = 0; i < titles.length; i++) {
                    if (i == scrolledPosition) {
                        break;
                    } else {
                        float textWidth = mPaint.measureText(titles[i]);
                        startX = startX + textWidth + spacing * 2;
                    }
                }
                float textWidth = mPaint.measureText(titles[scrolledPosition]);//当前textWidth宽度
                if (noscreen) {
                    float textFullWidth = mWidth / titles.length;
                    startX = textFullWidth * (scrolledPosition + 1) - (textFullWidth + textWidth) * 0.5f;
                }
                float stopX = startX + textWidth;
//                Log.v(TAG, "scrolledPosition=" + scrolledPosition);
                if (positionOffset > 0f && scrolledPosition < mCount - 1) {
                    float nextTextWidth = mPaint.measureText(titles[scrolledPosition + 1]);
                    float nextTextRight;
                    if (noscreen) {
                        float textFullWidth = mWidth / titles.length;
                        nextTextRight = textFullWidth * (scrolledPosition + 2) - (textFullWidth - nextTextWidth) * 0.5f;
                    } else {
                        nextTextRight = stopX + spacing * 2 + nextTextWidth;
                    }
                    final float nextTextLeft = nextTextRight - nextTextWidth;

                    float startOffset = getStartOffset(positionOffset);
                    float endOffset = getStopOffset(positionOffset);
                    startX = (int) (startOffset * nextTextLeft + (1.0f - startOffset) * startX);
                    stopX = (int) (endOffset * nextTextRight + (1.0f - endOffset) * stopX);
                }
                canvas.drawLine(startX, mHeight, stopX, mHeight, mLinePaint);
            }

            //绘制最下面的细线
            if (mLineHeight != 0) {
                if (mLinePaint.getColor() != mLineColor) {
                    mLinePaint.setColor(mLineColor);
                }
                mLinePaint.setStrokeWidth(mLineHeight);
                canvas.drawLine(0, mHeight, mWidth, mHeight, mLinePaint);
            }
        }

        private Interpolator startInterpolator, stopInterpolator;
        private static final float DEFAULT_INDICATOR_INTERPOLATION_FACTOR = 3.0f;

        private float getStartOffset(float offset) {
            if (!isDefault) {
                if (startInterpolator == null) {
                    startInterpolator = new AccelerateInterpolator(DEFAULT_INDICATOR_INTERPOLATION_FACTOR);
                }
                return startInterpolator.getInterpolation(positionOffset);
            }
            return offset;
        }

        private float getStopOffset(float offset) {
            if (!isDefault) {
                if (stopInterpolator == null) {
                    stopInterpolator = new DecelerateInterpolator(DEFAULT_INDICATOR_INTERPOLATION_FACTOR);
                }
                return stopInterpolator.getInterpolation(positionOffset);
            }
            return offset;
        }

        private boolean action_dwon;
        private float dwonX;

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    action_dwon = true;
                    dwonX = event.getX();
                    //Log.v("onTouchEvent", "按下");
                    return true;
                case MotionEvent.ACTION_UP:
                    //Log.v("onTouchEvent", "松开");
                    onCkick(event.getX());
                    break;
                case MotionEvent.ACTION_CANCEL:
                    onCkick(event.getX());
                    break;

            }
            return super.onTouchEvent(event);
        }

        // 点击事件处理
        private void onCkick(float upX) {
            if (action_dwon) {
                if (titles != null && titles.length > 0 && Math.abs(dwonX - upX) < mTouchSlop) {
                    float startX = 0;
                    for (int i = 0; i < titles.length; i++) {
                        float textWidth = mPaint.measureText(titles[i]);
                        if (noscreen) {
                            float textFullWidth = mWidth / titles.length;
                            startX = textFullWidth * (i + 1) - (textFullWidth + textWidth) * 0.5f - spacing;
                        }
                        if (dwonX > startX && dwonX < startX + textWidth + spacing * 2) {
                            if (upX > startX && upX < startX + textWidth + spacing * 2) {
                                if (i != mCurItem) {
                                    if (mOnClickListener == null) {
                                        if (Math.abs(mCurItem - i) > 2) {
                                            mViewPager.setCurrentItem(i, false);
                                        } else {
                                            mViewPager.setCurrentItem(i);
                                        }
                                    } else {
                                        mOnClickListener.onClickListener(i);
                                    }
                                } else {
                                    if (mOnClickListener != null) {
                                        mOnClickListener.onReClickListener(i);
                                    }
                                }
                            }
                            break;
                        }
                        if (!noscreen) {
                            startX = startX + textWidth + spacing * 2;
                        }
                    }
                }
            }
            action_dwon = false;
            dwonX = 0;
        }
    }


    public int dip2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public float sp2px(float size) {
        Context c = getContext();
        Resources r;
        if (c == null)
            r = Resources.getSystem();
        else
            r = c.getResources();

        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size, r.getDisplayMetrics());
    }
}
