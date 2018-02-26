package flatstack.com.roomarchcomponents;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.facebook.stetho.Stetho;

import flatstack.com.roomarchcomponents.data.repository.UserRepository;
import flatstack.com.roomarchcomponents.data.source.local.AppDatabase;
import flatstack.com.roomarchcomponents.data.source.remote.Api;
import flatstack.com.roomarchcomponents.screens.UserListViewModel;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class App extends Application {

    private Retrofit          retrofit;
    private Api               api;
    private UserRepository    userRepository;
    private UserListViewModel userListViewModel;
    private AppDatabase       appDatabase;

    @Override public void onCreate() {
        super.onCreate();
        initialize();
    }

    private void initialize() {
        retrofit = new Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build();
        api = retrofit.create(Api.class);
        appDatabase = Room.databaseBuilder(getApplicationContext(),
            AppDatabase.class, "app-database").build();
        userRepository = new UserRepository(api, appDatabase.userDAO());
        userListViewModel = new UserListViewModel(userRepository);
        initializeStetho();
    }

    private void initializeStetho() {
        // Create an InitializerBuilder
        Stetho.InitializerBuilder initializerBuilder =
            Stetho.newInitializerBuilder(this);

        // Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
            Stetho.defaultInspectorModulesProvider(this)
        );

        // Use the InitializerBuilder to generate an Initializer
        Stetho.Initializer initializer = initializerBuilder.build();

        // Initialize Stetho
        Stetho.initialize(initializer);
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public Api getApi() {
        return api;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public UserListViewModel getUserListViewModel() {
        return userListViewModel;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
