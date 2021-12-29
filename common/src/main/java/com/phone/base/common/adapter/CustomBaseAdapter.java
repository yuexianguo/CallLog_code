package com.phone.base.common.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class CustomBaseAdapter<T, VH extends CustomBaseAdapter.CustomBaseHolder> extends RecyclerView.Adapter<VH> {

    private List<T> list;

    private int layoutId;

    private OnItemClickListener<T> onItemClickListener;

    private OnItemLongClickListener<T> onItemLongClickListener;

    public interface OnItemClickListener<A> {
        void onItemClick(View view, A a, int position);
    }

    public interface OnItemLongClickListener<A> {
        boolean onItemLongClick(View view, A a, int position);
    }

    public CustomBaseAdapter(int layoutId, List<T> list) {
        this.list = list;
        this.layoutId = layoutId;
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    protected abstract @NonNull
    VH onCreateCustomViewHolder(@NonNull View view);

    protected abstract void onBindCustomViewHolder(@NonNull VH vh, T t);

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return onCreateCustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        onBindCustomViewHolder(holder, list.get(position));
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public abstract class CustomBaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private static final int CLICK_EVENT_TIME = 600;

        private long lastClickTime;

        public CustomBaseHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastClickTime >= CLICK_EVENT_TIME) {
                    int position = getAdapterPosition();
                    if (position == -1) {
                        return;
                    }
                    onItemClickListener.onItemClick(v, list.get(position), position);
                }
                lastClickTime = currentTime;
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (onItemLongClickListener != null) {
                int position = getAdapterPosition();
                if (position == -1) {
                    return false;
                }
               return onItemLongClickListener.onItemLongClick(v, list.get(position), position);
            }
            return false;
        }
    }

}
