package com.flatstack.android.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.flatstack.android.Deps;

import java.io.IOException;
import java.util.List;

import es.dmoral.toasty.Toasty;
import moe.banana.jsonapi2.Document;
import moe.banana.jsonapi2.Error;
import retrofit2.adapter.rxjava.HttpException;
import rx.functions.Action1;

/**
 * Created by ereminilya on 24/3/17.
 */

public class Errors {

    private static final String TAG = "Errors";

    public static void handle(Context context, Throwable error) {
        Document document = parseError((HttpException) error);
        String errors = Lists.listToString((List<Error>) document.errors(), "\n",
            er -> er.getTitle() + (er.getDetail() == null ? "" : (" " + er.getDetail()))
        );
        Toasty.error(context, errors, Toast.LENGTH_LONG).show();
        Log.e(TAG, "something bad happens", error);
    }

    private static Document parseError(HttpException error) {
        try {
            return Deps.jsonConverter().adapter(Document.class)
                .fromJson(error.response().errorBody().source());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Action1<Throwable> handle(Context context) {
        return throwable -> handle(context, throwable);
    }
}
