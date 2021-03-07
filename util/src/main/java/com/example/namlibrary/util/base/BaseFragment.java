package com.example.namlibrary.util.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseFragment<ItemBinding extends ViewBinding, T> extends Fragment {
    protected ItemBinding binding;
    protected Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Type superclass = getClass().getGenericSuperclass();
        Class< ? > aClass = (Class< ? >) ((ParameterizedType) superclass).getActualTypeArguments()[0];
        try {
            Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            binding = (ItemBinding) method.invoke(null, getLayoutInflater(), container, false);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initListAndAttachView();
        clickListener();
    }

    protected abstract void initListAndAttachView();

    protected abstract void onActive(T t);

    protected abstract void clickListener();
}
