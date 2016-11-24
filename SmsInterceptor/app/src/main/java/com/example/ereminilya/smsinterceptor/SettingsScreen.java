package com.example.ereminilya.smsinterceptor;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ereminilya.smsinterceptor.utils.di.Injector;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsScreen extends AppCompatActivity {

    @Inject Settings settings;

    @Bind(R.id.phone_to_spy)            TextView     uiPhoneToSpy;
    @Bind(R.id.sms_interception_toggle) SwitchCompat uiInterceptionEnabledToggle;
    @Bind(R.id.grant_permission)        View         uiGrantPermissionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);
        ButterKnife.bind(this);
        Injector.inject(this);
        if (savedInstanceState == null) {
            uiInterceptionEnabledToggle.setChecked(settings.isInterceptionEnabled());
            String phoneToSpy = settings.getPhoneToSpy();
            if (phoneToSpy != null) {
                uiPhoneToSpy.setText(phoneToSpy);
            }
        }
    }

    @Override protected void onResume() {
        super.onResume();
        int status = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        if (status != PackageManager.PERMISSION_GRANTED) {
            uiGrantPermissionBtn.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.grant_permission) void onGrantPermissioClick() {
        Dexter.checkPermission(new PermissionListener() {
            @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                Toast.makeText(SettingsScreen.this, R.string.receive_sms_granted, Toast.LENGTH_SHORT).show();
                uiGrantPermissionBtn.setVisibility(View.GONE);
            }

            @Override public void onPermissionDenied(PermissionDeniedResponse response) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

            }
        }, Manifest.permission.RECEIVE_SMS);
    }

    @OnClick(R.id.sms_interception_toggle) void onToggleClick(SwitchCompat toggle) {
        boolean finalState = toggle.isChecked();
        toggle.setChecked(finalState);
        settings.enableInterception(finalState);
    }

    @OnClick(R.id.save) void onSaveClick() {
        String phone = uiPhoneToSpy.getText().toString();
        settings.saveNumber(phone);
        Toast.makeText(this, getString(R.string.phone_updated, phone), Toast.LENGTH_SHORT).show();
    }

//    Пользователь может внести телефон отправителя, смс от которого нужно перехватывать.
//    Сообщение перехватывается и открывается экран с текстом этого сообщения.
//    Приложении деплоить и описание в один из репозиториев примеров.
}