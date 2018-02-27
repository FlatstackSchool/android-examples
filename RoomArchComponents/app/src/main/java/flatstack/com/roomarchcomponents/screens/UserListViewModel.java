package flatstack.com.roomarchcomponents.screens;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import flatstack.com.roomarchcomponents.data.entity.User;
import flatstack.com.roomarchcomponents.data.repository.UserRepository;
import io.reactivex.Observable;

public class UserListViewModel {

    private final UserRepository userRepository;

    public UserListViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Observable<List<User>> getUsers() {
        return userRepository.getUsers()
            .map(users -> {
                // Prepare the data for your UI, the users list
                // and maybe some additional data needed as well
                return users;
            }).onErrorReturn(throwable -> {
                Log.e("Error","Something went wrong" + throwable);
                return new ArrayList<User>();
            })
            ;
    }

}
