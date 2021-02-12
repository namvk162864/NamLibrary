package com.example.namlibrary.util.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public abstract class BaseAdapter<ItemBinding extends ViewBinding, T> extends RecyclerView.Adapter<BaseViewHolder<ItemBinding, T>> {
    private ArrayList<T> arrayList;

    public BaseAdapter(ArrayList<T> arrayList) {
        setArrayList(arrayList);
    }

    public ArrayList<T> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<T> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public BaseViewHolder<ItemBinding, T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Type superclass = getClass().getGenericSuperclass();
        Class<?> aClass = (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
        ItemBinding ib = null;
        try {
            Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            ib = (ItemBinding) method.invoke(null, LayoutInflater.from(parent.getContext()), parent, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBaseViewHolder(ib);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<ItemBinding, T> holder, int position) {
        holder.bind(arrayList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public abstract BaseViewHolder<ItemBinding, T> getBaseViewHolder(ItemBinding itemBinding);
}
