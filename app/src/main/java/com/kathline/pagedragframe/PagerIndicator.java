package com.kathline.pagedragframe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public final class PagerIndicator extends View implements ViewPager.OnPageChangeListener {
    private ViewPager _pager;
    private Paint _paint = new Paint(1);

    private int _mode = Mode.DOTS;
    private float _pad;
    private float _dotSize;
    private int _previousPage = -1;
    private int _realPreviousPage;

    // current position and offset
    private float _scrollOffset;
    private int _scrollPosition;

    // dot animations
    private float _shrinkFactor = 1.0f;
    private float _expandFactor = 1.5f;

    public static class Mode {
        public static final int DOTS = 0;
        public static final int LINES = 1;
    }

    public PagerIndicator(Context context) {
        this(context, null);
    }

    public PagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        _pad = dip2px(context, 4);
        _paint.setColor(Color.parseColor("#FF5231"));
        _paint.setStrokeWidth(dip2px(context, 4));
        _paint.setAntiAlias(true);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        _dotSize = getHeight() / 2;
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (_pager == null) return;
        int pageCount = _pager.getAdapter().getCount();
        switch (_mode) {
            case Mode.DOTS:
                float circlesWidth = pageCount * (_dotSize + _pad * 2);
                canvas.translate(getWidth() / 2 - circlesWidth / 2, 0f);

                if (_realPreviousPage != _pager.getCurrentItem()) {
                    _shrinkFactor = 1f;
                    _realPreviousPage = _pager.getCurrentItem();
                }

                for (int dot = 0; dot < pageCount; dot++) {
                    float stepFactor = 0.05f;
                    float smallFactor = 1.0f;
                    float largeFactor = 1.5f;
                    if (dot == _pager.getCurrentItem()) {
                        // draw shrinking dot
                        if (_previousPage == -1)
                            _previousPage = dot;
                        _shrinkFactor = clampFloat(_shrinkFactor + stepFactor, smallFactor, largeFactor);
                        canvas.drawCircle(_dotSize / 2 + _pad + (_dotSize + _pad * 2) * dot, (float) (getHeight() / 2), _shrinkFactor * _dotSize / 2, _paint);
                        if (_shrinkFactor != largeFactor)
                            invalidate();
                    } else if (dot != _pager.getCurrentItem() && dot == _previousPage) {
                        // draw expanding dot
                        _expandFactor = clampFloat(_expandFactor - stepFactor, smallFactor, largeFactor);
                        canvas.drawCircle(_dotSize / 2 + _pad + (_dotSize + _pad * 2) * dot, (float) (getHeight() / 2), _expandFactor * _dotSize / 2, _paint);
                        if (_expandFactor != smallFactor)
                            invalidate();
                        else {
                            _expandFactor = 1.5f;
                            _previousPage = -1;
                        }
                    } else {
                        // draw normal dot
                        canvas.drawCircle(_dotSize / 2 + _pad + (_dotSize + _pad * 2) * dot, (float) (getHeight() / 2), _dotSize / 2, _paint);
                    }
                }
                break;
            case Mode.LINES:
                float width = getWidth() / pageCount;
                float startX = (_scrollPosition + _scrollOffset) * width;
                float startY = getHeight() / 2;

                canvas.drawLine(startX, startY, startX + width, startY, _paint);
                if (_scrollOffset != 0f) invalidate();
                break;
        }
    }

    public final void setMode(int mode) {
        _mode = mode;
        invalidate();
    }

    public final void setViewPager(ViewPager pager) {
        if (pager == null && _pager != null) {
            _pager.removeOnPageChangeListener(this);
            _pager = null;
        } else {
            _pager = pager;
            if (pager != null) {
                pager.addOnPageChangeListener(this);
            }
        }
        invalidate();
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static float clampFloat(float target, float min, float max) {
        return Math.max(min, Math.min(max, target));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        _scrollOffset = positionOffset;
        _scrollPosition = position;
        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        // nothing
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // nothing
    }
}
