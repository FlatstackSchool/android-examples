package com.flatstack.analytics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.flatstack.analytics.util.AnalyticsHelper;
import com.flurry.android.Constants;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MainActivity extends AppCompatActivity {

    private final String SCREEN_NAME = "mainScreen";

    private AnalyticsHelper analyticsHelper;
    private String          paramKey;
    private String          paramValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        analyticsHelper = ((App) getApplication()).getAnalyticsHelper();
    }

    @Override protected void onStart() {
        super.onStart();
        analyticsHelper.onStart();
    }

    @Override protected void onStop() {
        super.onStop();
        analyticsHelper.onStop();
    }

    @Override protected void onResume() {
        super.onResume();
        analyticsHelper.onResume(SCREEN_NAME);
    }

    @OnClick(R.id.bt_simple_event) void performSimpleEvent() {
        analyticsHelper.simpleEvent();
    }

    @OnClick(R.id.bt_param_event) void performParamEvent() {
        analyticsHelper.paramEvent(paramKey, paramValue);
    }

    @OnClick(R.id.bt_error_event) void performErrorEvent() {
        analyticsHelper.errorEvent();
    }

    @OnClick(R.id.bt_user_info) void performUserInfo() {
        analyticsHelper.performUserInfo(new User("12345", "yaroslav.sudnik@flatstack.com",
            "Yaroslav Sudnik", 18, Constants.MALE));
    }

    @OnTextChanged(R.id.et_param_key) protected void textChangedKey(CharSequence charSequence) {
        paramKey = charSequence.toString();
    }

    @OnTextChanged(R.id.et_param_value) protected void textChangedValue(CharSequence charSequence) {
        paramValue = charSequence.toString();
    }

}
