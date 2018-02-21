package flatstack.com.roomarchcomponents.data.repository;

import android.support.annotation.NonNull;

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

    public Observable<User[]> getUsers() {
        return Observable.concatArray(
            getUsersFromDB(),
            getUsersFromApi()
        );
    }

    private Observable<User[]> getUsersFromDB() {
        return userDAO.getUsers();
    }

    private Observable<User[]> getUsersFromApi() {
        return api.getUsers()
            .doOnNext(this::cacheUsers);
    }

    private void cacheUsers(User[] users) {
        Observable.fromCallable(() -> userDAO.insert(users))
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe();
    }

}
