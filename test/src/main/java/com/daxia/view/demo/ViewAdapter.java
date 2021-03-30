package com.daxia.view.demo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.daxia.view.demo.activity.EQActivity;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.VH> {

    private Context context;
    private String[] data;

    public ViewAdapter(Context context, String[] data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final Button button = new Button(context);
        return new VH(button);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int position) {
        Button btn = (Button) vh.itemView;
        btn.setText(data[position]);
        btn.setTag(position);
        btn.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return data != null ? data.length : 0;
    }

    public class VH extends RecyclerView.ViewHolder {

        public VH(@NonNull View itemView) {
            super(itemView);
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getTag() instanceof Integer) {
                onClickToStartActivity((int) view.getTag());
            }
        }
    };

    private void onClickToStartActivity(int position) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (position == 0) {
            intent.setClass(context, EQActivity.class);
        }
        context.startActivity(intent);
    }
}
