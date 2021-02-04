package com.example.namlibrary.util.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseActivity<ItemBinding extends ViewBinding> extends AppCompatActivity {
    protected ItemBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initSharePre();

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

        initDataAndAttachView(savedInstanceState);
        clickListener();
    }

    protected abstract void initSharePre();

    protected abstract void initDataAndAttachView(Bundle savedInstanceState);

    protected abstract void clickListener();

    protected void requirePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            /*
             *  required:
             *  display pop-up windows while running in the background
             *  display pop-up window
             */
            Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            intent.putExtra("extra_pkgname", getPackageName());
            startActivity(intent);
        }
    }
}
