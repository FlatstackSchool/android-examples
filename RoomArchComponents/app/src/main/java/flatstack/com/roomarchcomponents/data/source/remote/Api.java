package flatstack.com.roomarchcomponents.data.source.remote;

import java.util.List;

import flatstack.com.roomarchcomponents.data.entity.User;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Api {

    @GET("users")
    Observable<List<User>> getUsers();
}
