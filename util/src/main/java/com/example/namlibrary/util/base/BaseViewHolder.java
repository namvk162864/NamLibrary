package com.example.namlibrary.util.base;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

public abstract class BaseViewHolder<ItemBinding extends ViewBinding, T> extends RecyclerView.ViewHolder {
    T t;
    ItemBinding itemBinding;

    public BaseViewHolder(ItemBinding itemBinding) {
        super(itemBinding.getRoot());
        this.itemBinding = itemBinding;
    }

    public void bind(T t, int position) {
        this.t = t;
        bindView(t, position);

        clickListener(t, position);
    }

    public abstract void bindView(T t, int position);

    public abstract void clickListener(T t, int position);
}
