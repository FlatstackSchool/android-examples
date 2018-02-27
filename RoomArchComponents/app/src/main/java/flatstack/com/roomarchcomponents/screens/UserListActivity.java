package flatstack.com.roomarchcomponents.screens;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import flatstack.com.roomarchcomponents.App;
import flatstack.com.roomarchcomponents.R;
import flatstack.com.roomarchcomponents.data.entity.User;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserListActivity extends Activity {

    @BindView(R.id.lv_users) ListView          uiUsersLv;
    private                  Unbinder          bindings;
    private                  UserListViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        ButterKnife.bind(this);
        viewModel = ((App) getApplication().getApplicationContext()).getUserListViewModel();
    }

    @Override protected void onStart() {
        super.onStart();
        Observable.just(viewModel.getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                users -> showUsers(users),
                error -> showEror(error)));

    }

    private void showUsers(List<User> users) {
        if (!users.isEmpty())
            uiUsersLv.setAdapter(new ArrayAdapter<User>(this,
                android.R.layout.simple_list_item_1, users));
    }

    private void showEror(Throwable error) {
        Toast.makeText(this,
            "can't load users" + error.getMessage(), Toast.LENGTH_LONG).show();
    }
}
