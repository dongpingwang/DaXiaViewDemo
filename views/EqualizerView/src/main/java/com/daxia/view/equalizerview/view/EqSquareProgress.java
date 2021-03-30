package com.daxia.view.equalizerview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.daxia.view.equalizerview.R;
import com.daxia.view.equalizerview.util.ResUtils;

/**
 * @author Dongping Wang
 * date 2020/3/422:54
 * email 927718579@qq.com
 */
public class EqSquareProgress extends View {

    private Paint paint;
    private Path cursorPath;
    private float perLineHeight;
    private int max, min, step, colorDefault, colorAdjusting, colorAdjusted;
    private int progress;
    private boolean adjusting;

    public EqSquareProgress(Context context) {
        this(context, null);
    }

    public EqSquareProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EqSquareProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        perLineHeight = ResUtils.getDimension(R.dimen.eq_progress_line);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(perLineHeight);
        cursorPath = new Path();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EqSquareProgress);
        max = typedArray.getInteger(R.styleable.EqSquareProgress_max, 14);
        min = typedArray.getInteger(R.styleable.EqSquareProgress_min, -14);
        step = typedArray.getInteger(R.styleable.EqSquareProgress_step, 1);
        colorDefault = typedArray.getColor(R.styleable.EqSquareProgress_default_color, ResUtils.getColor(R.color.eq_progress_default_color));
        colorAdjusting = typedArray.getColor(R.styleable.EqSquareProgress_adjusting_color, ResUtils.getColor(R.color.eq_progress_adjusting_color));
        colorAdjusted = typedArray.getColor(R.styleable.EqSquareProgress_adjusted_color, ResUtils.getColor(R.color.eq_progress_adjusted_color));
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int count = (max - min) / step + 1;
        for (int i = 0; i < count; i++) {
            // 从下往上绘制
            float startX = getPaddingLeft();
            float startY = getHeight() - ResUtils.getDimension(R.dimen.eq_progress_line_all) * i - perLineHeight;
            float stopX = getWidth() - getPaddingRight();
            float stopY = startY;
            if (min + i * step <= progress) {
                paint.setColor(colorAdjusted);
            } else {
                paint.setColor(colorDefault);
            }
            if (adjusting) {
                if (min + i * step <= progress) {
                    paint.setColor(colorAdjusting);
                }
            }
            canvas.drawLine(startX, startY, stopX, stopY, paint);
            // 绘制游标
            if (adjusting && min + i * step == progress) {
                paint.setColor(colorAdjusting);
                float cursorMargin = ResUtils.getDimension(R.dimen.eq_progress_cursor_margin);
                float cursorHeight = ResUtils.getDimension(R.dimen.eq_progress_cursor_size);
                float cursorWidth = (float) (cursorHeight * 1.0F / 2 * Math.tan(Math.PI / 3));
                cursorPath.reset();
                cursorPath.moveTo(startX - cursorMargin, startY);
                cursorPath.lineTo(startX - cursorMargin - cursorWidth, startY - cursorHeight * 1.0F / 2);
                cursorPath.lineTo(startX - cursorMargin - cursorWidth, startY + cursorHeight * 1.0F / 2);
                cursorPath.lineTo(startX - cursorMargin, startY);
                cursorPath.close();
                canvas.drawPath(cursorPath, paint);
                cursorPath.reset();
                cursorPath.moveTo(stopX + cursorMargin, stopY);
                cursorPath.lineTo(stopX + cursorMargin + cursorWidth, stopY - cursorHeight * 1.0F / 2);
                cursorPath.lineTo(stopX + cursorMargin + cursorWidth, stopY + cursorHeight * 1.0F / 2);
                cursorPath.lineTo(stopX + cursorMargin, stopY);
                this.cursorPath.close();
                canvas.drawPath(cursorPath, paint);
            }
        }
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        this.progress = this.progress > max ? max : this.progress < min ? min : this.progress;
        invalidate();
    }

    public void setAdjusting(boolean adjusting) {
        this.adjusting = adjusting;
        invalidate();
    }

    public void setMax(int max) {
        this.max = max;
        invalidate();
    }

    public void setMin(int min) {
        this.min = min;
        invalidate();
    }

    public void setStep(int step) {
        this.step = step;
        invalidate();
    }
}
