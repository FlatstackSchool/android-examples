package fs.com.permissionexample.activities;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import butterknife.ButterKnife;
import butterknife.OnClick;
import fs.com.permissionexample.R;
import fs.com.permissionexample.utils.ContactHelper;

/**
 * Created by Ramil on 09/02/16.
 */
public class TakePermissionWithLibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_add_contact)
    void addContact() {
        Dexter.checkPermission(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                ContactHelper.insertContact(TakePermissionWithLibraryActivity.this);
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(TakePermissionWithLibraryActivity.this, "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }, Manifest.permission.WRITE_CONTACTS);
    }
}
