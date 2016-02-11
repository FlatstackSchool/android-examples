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

    @OnClick(R.id.btn_library) void openLibraryVersion() {
        startActivity(new Intent(this, LibraryVersionActivity.class));
    }

    @OnClick(R.id.btn_native) void openNativeVersion() {
        startActivity(new Intent(this, NativeVersionActivity.class));
    }
}
