package flatstack.com.roomarchcomponents.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import flatstack.com.roomarchcomponents.App;
import flatstack.com.roomarchcomponents.R;
import flatstack.com.roomarchcomponents.data.entity.User;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserListFragment extends BaseFragment {

    @BindView(R.id.lv_users) ListView uiUsersLv;

    private UserListViewModel viewModel
        = ((App) getActivity().getApplicationContext()).getUserListViewModel();

    @Override protected ActivityInfo getActivityInfo() {
        return new ActivityInfo(R.layout.fragment_user_list);
    }

    @Override public void onStart() {
        super.onStart();
        subscribe(viewModel.getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                users -> showUsers(users),
                error -> showEror(error)));
    }

    private void showUsers(List<User> users) {
        uiUsersLv.setAdapter(new ArrayAdapter<User>(getActivity(),
            android.R.layout.simple_list_item_1, users));
    }

    private void showEror(Throwable error) {
        Toast.makeText(getActivity(),
            "can't load users" + error.getMessage(), Toast.LENGTH_LONG).show();
    }
}
