package com.example.namlibrary.util.base;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewbinding.ViewBinding;

import com.example.namlibrary.util.data.sharepre.SharePreNightMode;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AbstractMainActivity<ItemBinding extends ViewBinding> extends AppCompatActivity {
    protected ItemBinding binding;
    Bundle savedInstanceState;
    SharePreNightMode sharePreNightMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setNightMode();
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        Type superclass = getClass().getGenericSuperclass();
        Class<?> aClass = (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
        try {
            Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class);
            binding = (ItemBinding) method.invoke(null, getLayoutInflater());
            setContentView(binding.getRoot());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(getPermissions(), 1000);
        } else {
            onActive();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000) {
            for (int result : grantResults)
                if (result == PackageManager.PERMISSION_DENIED) return;
            onActive();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void setNightMode() {
        // set state dark mode
        sharePreNightMode = new SharePreNightMode(this, getNameSharePreNightMode());
        int isNightMode = sharePreNightMode.getNightMode();
        AppCompatDelegate.setDefaultNightMode(isNightMode);
    }

    public String getNameSharePreNightMode() {
        return "nameSharePreNightMode";
    }

    public abstract String[] getPermissions();

    private void onActive() {
        initSharePre();
        initDataAndAttachView(savedInstanceState);
        clickListener();
    }

    public abstract void initSharePre();

    public abstract void initDataAndAttachView(Bundle savedInstanceState);

    public abstract void clickListener();
}
