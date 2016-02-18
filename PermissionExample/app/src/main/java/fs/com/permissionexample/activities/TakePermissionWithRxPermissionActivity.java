package fs.com.permissionexample.activities;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.ButterKnife;
import butterknife.OnClick;
import fs.com.permissionexample.R;
import fs.com.permissionexample.utils.ContactHelper;
import rx.functions.Action1;

/**
 * Created by Ilya Eremin on 18.02.2016.
 */
public class TakePermissionWithRxPermissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_add_contact) void addContact() {
        RxPermissions.getInstance(this)
            .request(Manifest.permission.WRITE_CONTACTS)
            .subscribe(new Action1<Boolean>() {
                @Override public void call(Boolean granted) {
                    if (granted) {
                        ContactHelper.insertContact(TakePermissionWithRxPermissionActivity.this);
                    } else {
                        Toast.makeText(TakePermissionWithRxPermissionActivity.this, "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT).show();
                    }
                }
            })
        ;
    }
}
