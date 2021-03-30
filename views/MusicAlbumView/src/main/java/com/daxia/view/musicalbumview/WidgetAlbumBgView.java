package com.daxia.view.musicalbumview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Author WangDongPing
 * Date: 2020/8/13
 * 带有渐变与线条的圆环
 */
public class WidgetAlbumBgView extends View {

    private Paint paint;
    // 圆环半径
    private int ringWidth;
    // 渐变色
    private int[] colors;
    private SweepGradient gradient;
    // 圆线距圆环内边的距离
    private int[] ringLinesMarginOut = {
            dp2px(3.78F),
            dp2px(7.03F),
            dp2px(10.27F),
            dp2px(12.97F)
    };
    // 圆线高度
    private int ringLineWidth;

    public WidgetAlbumBgView(Context context) {
        this(context, null);
    }

    public WidgetAlbumBgView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WidgetAlbumBgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        colors = new int[]{getColor(R.color.widget_album_ring_color1), getColor(R.color.widget_album_ring_color2),
                getColor(R.color.widget_album_ring_color1), getColor(R.color.widget_album_ring_color2),
                getColor(R.color.widget_album_ring_color1)};

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WidgetAlbumBgView);
        ringWidth = (int) typedArray.getDimension(R.styleable.WidgetAlbumBgView_ring_width, getResources().getDimension(R.dimen.widget_album_ring_width));
        ringLineWidth = (int) typedArray.getDimension(R.styleable.WidgetAlbumBgView_ring_line_width, getResources().getDimension(R.dimen.widget_album_ring_line_width));
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStrokeWidth(ringWidth);
        paint.setColor(getColor(R.color.widget_album_ring_color1));
        if (gradient == null) {
            gradient = new SweepGradient(getWidth() * 0.5F, getHeight() * 0.5F, colors, new float[]{
                    0F, 0.25F, 0.5F, 0.75F, 1F
            });
        }
        paint.setShader(gradient);
        canvas.drawCircle(getWidth() * 0.5F, getHeight() * 0.5F, getWidth() * 0.5F - ringWidth, paint);
        paint.setShader(null);
        paint.setStrokeWidth(ringLineWidth);
        paint.setColor(getColor(R.color.widget_album_ring_line_color));
        for (int marginOut : ringLinesMarginOut) {
            canvas.drawCircle(getWidth() * 0.5F, getHeight() * 0.5F, getWidth() * 0.5F - marginOut - ringLineWidth * 0.5F, paint);
        }
    }

    private int dp2px(float dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5F);
    }

    @ColorInt
    private int getColor(@ColorRes int colorId) {
        return getResources().getColor(colorId, null);
    }

}

