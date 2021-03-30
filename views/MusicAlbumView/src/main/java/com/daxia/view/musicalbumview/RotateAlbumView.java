package com.daxia.view.musicalbumview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;


/**
 * Author WangDongPing
 * Date: 20-10-13
 * 旋转的音乐专辑View
 */
public class RotateAlbumView extends FrameLayout {

    private static final String TAG = "RotateAlbumView";
    private ImageView ivAlbumPic;
    private ObjectAnimator animator;

    public RotateAlbumView(@NonNull Context context) {
        this(context, null);
    }

    public RotateAlbumView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotateAlbumView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // 设置旋转动画(属性动画)
    private void init(Context context) {
        View.inflate(context, R.layout.view_rotate_album, this);
        ivAlbumPic = (ImageView) findViewById(R.id.view_pic);
        animator = ObjectAnimator.ofFloat(ivAlbumPic, "rotation", 0.0F, 360.0F);
        animator.setDuration(10 * 1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        setPlaying(true);
    }

    // 更新播放状态
    public void setPlaying(boolean isPlaying) {
        Log.d(TAG, "update RotateAlbumView: isPlaying = " + isPlaying);
        if (isPlaying) {
            if (!animator.isRunning()) {
                animator.start();
            } else {
                animator.resume();
            }
        } else {
            if (!animator.isStarted() || !animator.isRunning()) {
                animator.cancel();
            }
            animator.pause();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "RotateAlbumView: onDetachedFromWindow");
        animator.cancel();
    }

    // 如果正方形的图片边缘出现漏出，可以把图片裁切成圆形
    private Drawable getRoundBitmapDrawable(@NonNull Bitmap bitmap) {
        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        if (drawable != null) {
            drawable.setCircular(true);
            drawable.setAntiAlias(true);
        }
        return drawable;
    }
}
