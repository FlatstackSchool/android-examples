package flatstack.com.roomarchcomponents.data.source.local.users;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import flatstack.com.roomarchcomponents.data.entity.User;
import io.reactivex.Observable;
import io.reactivex.Single;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM user")
    Single<List<User>> getUsers();

    @Insert(onConflict = REPLACE)
    List<Long> insert(List<User> users);
}
