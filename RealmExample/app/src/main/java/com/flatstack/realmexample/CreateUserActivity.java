package com.flatstack.realmexample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;

public class CreateUserActivity extends AppCompatActivity {

    @Bind(R.id.et_name)    EditText uiNameEt;
    @Bind(R.id.et_surname) EditText uiSurnameEt;
    @Bind(R.id.et_phone)   EditText uiPhoneEt;
    @Bind(R.id.spinner)    Spinner  uiSpinner;

    private static final String EXTRA_NAME_USER = "extraUser";

    public static Intent intent(Context context, String userPhone) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_NAME_USER, userPhone);
        return new Intent(context, CreateUserActivity.class).putExtras(bundle);
    }

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        setContentView(R.layout.activity_create_user);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        checkExtras();
    }

    private void checkExtras() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            String userPhone = bundle.getString(EXTRA_NAME_USER);
            if (userPhone != null) {
                changeUserMode(userPhone);
            }
        }
    }

    private void createUser() {
        String name = uiNameEt.getText().toString();
        String surname = uiSurnameEt.getText().toString();
        String phone = uiPhoneEt.getText().toString();
        String group = uiSpinner.getSelectedItem().toString();
        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, R.string.error_user_create, Toast.LENGTH_SHORT).show();
        } else {
            creatingUser(name, surname, phone, group);
        }
    }

    private void changeUserMode(String userPhone) {
        realm.executeTransaction(realm1 -> {
            User user = realm1.where(User.class).equalTo("phone", userPhone).findFirst();
            uiNameEt.setText(user.getName());
            uiPhoneEt.setText(user.getPhone());
            uiPhoneEt.setEnabled(false);
        });
    }

    private void creatingUser(String name, String surname, String phone, String groupName) {
        realm.executeTransaction(realm1 -> {
            Group group = realm1.where(Group.class).equalTo("name", groupName).findFirst();
            if (group == null) {
                Number id = realm.where(Group.class).max("id");
                if (id == null) {
                    group = realm1.createObject(Group.class, 0);
                } else {
                    group = realm1.createObject(Group.class, (long) id + 1);
                }
                group.setName(groupName);
                group.setUsers(new RealmList<>());
            }
            User user = realm1.copyToRealmOrUpdate(new User(name, surname, phone, group.getId()));
            group.getUsers().add(user);
        });
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_saving:
                createUser();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

}
