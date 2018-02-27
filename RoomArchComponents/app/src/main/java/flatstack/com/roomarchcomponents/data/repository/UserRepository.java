package flatstack.com.roomarchcomponents.data.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import flatstack.com.roomarchcomponents.data.entity.User;
import flatstack.com.roomarchcomponents.data.source.local.users.UserDAO;
import flatstack.com.roomarchcomponents.data.source.remote.Api;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class UserRepository {

    @NonNull private final Api     api;
    @NonNull private final UserDAO userDAO;

    public UserRepository(Api api, UserDAO userDAO) {
        this.api = api;
        this.userDAO = userDAO;
    }

    public Observable<List<User>> getUsers() {
        return Observable.concatArray(
            getUsersFromDB(),
            getUsersFromApi()
        );
    }

    private Observable<List<User>> getUsersFromDB() {
        return userDAO.getUsers().toObservable();
    }

    private Observable<List<User>> getUsersFromApi() {
        return api.getUsers()
            .doOnNext(users -> cacheUsers(users))
            .onErrorReturn(throwable -> {
                Log.e("UserRepository",
                    "can't access api, returning empty List<User> " + throwable);
                return new ArrayList<User>();
            });
    }

    private void cacheUsers(List<User> users) {
        Observable.fromCallable(() -> userDAO.insert(users))
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe();
    }

}
