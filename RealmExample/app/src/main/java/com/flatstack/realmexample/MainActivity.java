package com.flatstack.realmexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity implements MainView {

    @Bind(R.id.recycler_view) RecyclerView recyclerView;

    private Realm      realm;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerViewAdapter(this, getUsers()));
    }

    @Override public void changeUser(User user) {
        startActivity(CreateUserActivity.intent(this, user.getPhone()));
    }

    @Override public void deleteUser(User user) {
        realm.executeTransaction(realm1 -> {
            RealmResults<User> result = realm1.where(User.class).equalTo("phone",
                user.getPhone()).findAll();
            result.deleteAllFromRealm();
        });
    }

    public void searchUser(String... s) {
        recyclerView.setAdapter(new RecyclerViewAdapter(this, getUsers(s)));
    }

    private RealmResults<User> getUsers(String... s) {
        if (s.length != 0) {
            return realm.where(User.class).contains("name", s[0]).findAll();
        } else {
            return realm.where(User.class).findAllAsync();
        }
    }

    private void initSearching() {
        Observable.create((Observable.OnSubscribe<String>) subscriber ->
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override public boolean onQueryTextSubmit(String query) {
                    subscriber.onNext(query);
                    return true;
                }

                @Override public boolean onQueryTextChange(String newText) {
                    subscriber.onNext(newText);
                    return true;
                }
            })
        )
            .debounce(200, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::searchUser);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        initSearching();

        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_creating:
                startActivity(new Intent(this, CreateUserActivity.class));
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
