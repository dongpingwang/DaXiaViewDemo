package com.daxia.view.equalizerview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daxia.view.equalizerview.R;
import com.daxia.view.equalizerview.util.ResUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Dongping Wang
 * date 2020/3/511:59
 * email 927718579@qq.com
 */
public class EqSquareBars extends RecyclerView {

    private static final int[] EQ_FREQUENCIES = ResUtils.getIntArray(R.array.eq_frequencies);

    private static final int REGION_BASS = 4;
    private static final int REGION_ALTO = 9;
    private static final int REGION_COUNT = 3;

    private ProgressAdapter adapter;
    private List<DataBean> dataBeans;
    // 调节的是哪个区域，即第几条Bar
    private int region;
    private Paint paintText;
    private Paint paintLine;
    private OnRegionChangeListener regionChangeListener;

    public EqSquareBars(Context context) {
        this(context, null);
    }

    public EqSquareBars(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EqSquareBars(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        // 去掉条目更新动画，防止调节时闪烁
        setItemAnimator(null);
        setHasFixedSize(true);
        initData();
        initView();
    }

    private void initData() {
        dataBeans = new ArrayList<>();
        adapter = new ProgressAdapter();
        paintText = new Paint();
        paintText.setAntiAlias(true);
        paintText.setTextSize(ResUtils.getDimension(R.dimen.eq_progress_font));
        paintLine = new Paint();
        paintLine.setColor(ResUtils.getColor(R.color.eq_progress_adjusting_color));
        paintLine.setStrokeWidth(ResUtils.getDimension(R.dimen.eq_progress_top_line));
        String[] titles = getResources().getStringArray(R.array.eq_titles_13);
        for (int i = 0; i < titles.length; i++) {
            DataBean dataBean = new DataBean();
            dataBean.title = titles[i];
            dataBean.freq = EQ_FREQUENCIES[i];
            this.dataBeans.add(dataBean);
        }
    }

    private void initView() {
        setAdapter(adapter);
        setHasFixedSize(true);
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        float y = getPaddingTop() + ResUtils.getDimension(R.dimen.eq_progress_font_height);
        float item = ResUtils.getDimension(R.dimen.eq_progress_item_width);
        float x;
        float startX1, startY1, stopX1, stopY1;
        float startX2, startY2, stopX2, stopY2;
        startY1 = stopY1 = startY2 = stopY2 = y - 0.30F * ResUtils.getDimension(R.dimen.eq_progress_font_height);
        if (region < REGION_BASS) {
            // 低音
            startX1 = getPaddingLeft() + item * 0.3F;
            stopX1 = item * 1.5F;
            startX2 = item * 2.4F;
            stopX2 = item * 3.7F;
        } else if (region < REGION_ALTO) {
            // 中音
            startX1 = item * 4.3F;
            stopX1 = item * 6F;
            startX2 = item * 7F;
            stopX2 = item * 8.7F;
        } else {
            // 高音
            startX1 = item * 9.2F;
            stopX1 = item * 10.5F;
            startX2 = item * 11.4F;
            stopX2 = item * 12.7F;
        }
        for (int i = 0; i < REGION_COUNT; i++) {
            x = item * (1.7F + 4.5F * i);
            String text = i == 0 ? ResUtils.getString(R.string.bass) : i == 1 ? ResUtils.getString(R.string.alto) : ResUtils.getString(R.string.high);
            if (i == 0 && region < REGION_BASS) {
                paintText.setColor(ResUtils.getColor(R.color.eq_progress_adjusting_color));
            } else if (i == 1 && region >= REGION_BASS && region < REGION_ALTO) {
                paintText.setColor(ResUtils.getColor(R.color.eq_progress_adjusting_color));
            } else if (i == 2 && region >= REGION_ALTO) {
                paintText.setColor(ResUtils.getColor(R.color.eq_progress_adjusting_color));
            } else {
                paintText.setColor(ResUtils.getColor(R.color.text_color_normal8));
            }
            c.drawText(text, x, y, paintText);
        }
        c.drawLine(startX1, startY1, stopX1, stopY1, paintLine);
        c.drawLine(startX2, startY2, stopX2, stopY2, paintLine);
    }

    public void setProgress(int progress) {
        setProgress(region, progress);
    }

    private void setProgress(int region, int progress) {
        if (region < 0 || region >= dataBeans.size()) {

        } else {
            DataBean dataBean = dataBeans.get(region);
            dataBean.value = progress;
            dataBeans.set(region, dataBean);
            adapter.notifyItemChanged(region);
        }
    }

    public int getProgress() {
        return getProgress(region);
    }

    private int getProgress(int region) {
        if (region < 0 || region >= dataBeans.size()) {
            return 0;
        }
        return dataBeans.get(region).value;
    }

    public void setOnRegionChangeListener(OnRegionChangeListener regionChangeListener) {
        this.regionChangeListener = regionChangeListener;
    }

    private class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.VHolder> implements View.OnClickListener {
        @NonNull
        @Override
        public VHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.view_eq_square_bars, null);
            VHolder holder = new VHolder(itemView);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull VHolder vHolder, int position) {
            DataBean dataBean = dataBeans.get(position);
            vHolder.setTitle(dataBean.title);
            vHolder.setProgress(dataBean.value);
            vHolder.setValue(dataBean.value);
            vHolder.setAdjusting(position == region);
            vHolder.itemView.setTag(position);
            vHolder.itemView.setOnClickListener(this);
        }

        @Override
        public int getItemCount() {
            return dataBeans.size();
        }

        @Override
        public void onClick(View view) {
            if (view.getTag() instanceof Integer) {
                region = (int) view.getTag();
                notifyDataSetChanged();
                if (regionChangeListener != null) {
                    regionChangeListener.onRegionChanged(region);
                }
            }
        }

        private class VHolder extends ViewHolder {
            private TextView tvValue;
            private EqSquareProgress eqProgress;
            private TextView tvTitle;

            private VHolder(@NonNull View itemView) {
                super(itemView);
                tvValue = (TextView) itemView.findViewById(R.id.tv_value);
                eqProgress = (EqSquareProgress) itemView.findViewById(R.id.pb_progress);
                tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            }

            private void setTitle(String title) {
                tvTitle.setText(title);
            }

            private void setValue(int value) {
                tvValue.setText(String.valueOf(value));
            }

            private void setProgress(int progress) {
                eqProgress.setProgress(progress);
            }

            private void setAdjusting(boolean isAdjusting) {
                eqProgress.setAdjusting(isAdjusting);
            }
        }
    }

    private static class DataBean {
        int value;
        int freq;
        String title;
    }

    public interface OnRegionChangeListener {
        void onRegionChanged(int region);
    }
}
