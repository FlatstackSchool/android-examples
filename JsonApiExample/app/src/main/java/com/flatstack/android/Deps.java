package com.flatstack.android;

import android.content.Context;
import android.preference.PreferenceManager;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.flatstack.android.models.ToDo;
import com.flatstack.android.models.User;
import com.flatstack.android.utils.jsonapi.JsonApiConverterFactory;
import com.flatstack.android.utils.storage.IStorage;
import com.flatstack.android.utils.storage.Storage;
import com.google.gson.Gson;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import moe.banana.jsonapi2.ResourceAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by ereminilya on 24/3/17.
 */

public class Deps {

    private static Context appContext;

    public static void init(Context context) {
        appContext = context;
    }

    private static Storage storage;

    public static IStorage storage() {
        if (storage == null) {
            storage = new Storage(PreferenceManager.getDefaultSharedPreferences(appContext), new Gson());
        }
        return storage;
    }

    private static Api api;

    public static Api api() {
        if (api == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(chain -> {
                    String token = storage().getString(IStorage.KEY_TOKEN);
                    String email = storage().getString(IStorage.KEY_EMAIL);
                    Request request = chain.request();
                    if (token != null && !token.isEmpty() && email != null && !email.isEmpty()) {
                        request = request.newBuilder()
                            .addHeader("X-User-Token", token)
                            .addHeader("X-User-Email", email)
                            .build();
                    }
                    return chain.proceed(request);
                })
                .build();
            api = new Retrofit.Builder()
                .client(client)
                .baseUrl(Api.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(jsonConverter()))
                .addConverterFactory(JsonApiConverterFactory.create(jsonConverter()))
                .build()
                .create(Api.class);
        }
        return api;
    }

    private static Moshi moshi;

    public static Moshi jsonConverter() {
        if (moshi == null) {
            JsonAdapter.Factory jsonApiAdapterFactory = ResourceAdapterFactory.builder()
                .add(User.class)
                .add(ToDo.class)
                .build();
            moshi = new Moshi.Builder()
                .add(jsonApiAdapterFactory)
                .build();
        }
        return moshi;
    }
}