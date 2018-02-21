package flatstack.com.roomarchcomponents.data.source.local.users;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import flatstack.com.roomarchcomponents.data.entity.User;
import io.reactivex.Observable;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM user")
    Observable<User[]> getUsers();

    @Insert(onConflict = REPLACE)
    Observable<User[]> insert(User[] users);
}
