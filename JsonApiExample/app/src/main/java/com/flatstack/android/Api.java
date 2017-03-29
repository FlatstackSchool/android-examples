package com.flatstack.android;

import com.flatstack.android.models.ToDo;
import com.flatstack.android.models.Token;
import com.flatstack.android.models.User;

import moe.banana.jsonapi2.ArrayDocument;
import moe.banana.jsonapi2.Document;
import moe.banana.jsonapi2.ObjectDocument;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by ereminilya on 24/3/17.
 */

public interface Api {

    String BASE_URL = "https://fs-rails-base-api-pr-216.herokuapp.com/";

    @Headers({
        "Accept: application/vnd.api+json",
        "Content-Type: application/vnd.api+json"
    })
    @POST("users/") Observable<Document<User>> register(@Body Document<User> userToRegister);

    @Headers({
        "Accept: application/vnd.api+json",
        "Content-Type: application/vnd.api+json"
    })
    @POST("sessions/") Observable<ObjectDocument<Token>> signIn(@Body Document<User> user);

    @Headers({
        "Accept: application/vnd.api+json",
        "Content-Type: application/vnd.api+json"
    })
    @GET("todo-items/") Observable<ArrayDocument<ToDo>> todos();

    @Headers({
        "Accept: application/vnd.api+json",
        "Content-Type: application/vnd.api+json"
    })
    @POST("todo-items/")
    Observable<ResponseBody> createToDo(@Body Document<ToDo> todo);

    @Headers({
        "Accept: application/vnd.api+json",
        "Content-Type: application/vnd.api+json"
    })
    @PATCH("todo-items/{id}")
    Observable<ResponseBody> updateToDo(@Path("id") String todoId, @Body Document<ToDo> todo);
}