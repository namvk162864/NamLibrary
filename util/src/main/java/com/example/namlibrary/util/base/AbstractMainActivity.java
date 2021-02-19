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
    protected Bundle savedInstanceState;
    protected SharePreNightMode sharePreNightMode;

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

        welcome();

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

    protected void setNightMode() {
        // set state dark mode
        sharePreNightMode = new SharePreNightMode(this, getNameSharePreNightMode());
        int isNightMode = sharePreNightMode.getNightMode();
        AppCompatDelegate.setDefaultNightMode(isNightMode);
    }

    protected String getNameSharePreNightMode() {
        return "nameSharePreNightMode";
    }

    protected abstract String[] getPermissions();

    protected void onActive() {
        initSharePre();
        initDataAndAttachView(savedInstanceState);
        clickListener();
    }

    protected void welcome() {
        // TODO something here to welcome
        // This is a splash screen
    }

    protected abstract void initSharePre();

    protected abstract void initDataAndAttachView(Bundle savedInstanceState);

    protected abstract void clickListener();
}
