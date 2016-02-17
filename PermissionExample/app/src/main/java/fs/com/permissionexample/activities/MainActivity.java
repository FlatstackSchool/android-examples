package fs.com.permissionexample.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import fs.com.permissionexample.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_dexter) void openLibraryVersion() {
        startActivity(new Intent(this, TakePermissionWithLibraryActivity.class));
    }

    @OnClick(R.id.btn_custom) void openNativeVersion() {
        startActivity(new Intent(this, TakePermissionActivity.class));
    }

    @OnClick(R.id.btn_rx_permission) void onRxPermissionClick(){
        startActivity(new Intent(this, TakePermissionWithRxPermissionActivity.class));
    }
}
