package com.flatstack.analytics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.flatstack.analytics.util.AnalyticsHelper;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MainActivity extends AppCompatActivity {

    private final String SCREEN_NAME = "mainScreen";

    private String          simpleEvent;
    private String          paramKey;
    private String          paramValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override protected void onStart() {
        super.onStart();
        AnalyticsHelper.get().onStartSession(this);
    }

    @Override protected void onStop() {
        super.onStop();
        AnalyticsHelper.get().onStopSession(this);
    }

    @OnClick(R.id.bt_simple_event) void performSimpleEvent() {
        AnalyticsHelper.get().log(simpleEvent);
    }

    @OnClick(R.id.bt_param_event) void performParamEvent() {
        Map<String, String> map = new HashMap<>();
        map.put(paramKey, paramValue);
        AnalyticsHelper.get().logParam("Custom Event", map);
    }

    @OnClick(R.id.bt_error_event) void performErrorEvent() {
        AnalyticsHelper.get().logError(SCREEN_NAME, "Error Click", new RuntimeException());
    }

    @OnTextChanged(R.id.et_simple_event) protected void textChangedSimpleValue(
        CharSequence charSequence) {
        simpleEvent = charSequence.toString();
    }

    @OnTextChanged(R.id.et_param_key) protected void textChangedKey(CharSequence charSequence) {
        paramKey = charSequence.toString();
    }

    @OnTextChanged(R.id.et_param_value) protected void textChangedValue(CharSequence charSequence) {
        paramValue = charSequence.toString();
    }

}
