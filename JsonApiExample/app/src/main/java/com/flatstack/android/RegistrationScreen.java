package com.flatstack.android;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.Toast;

import com.flatstack.android.models.User;
import com.flatstack.android.utils.Errors;
import com.flatstack.android.utils.Rxs;
import com.flatstack.android.utils.TextViewUtils;
import com.flatstack.android.utils.ui.BaseActivity;
import com.flatstack.android.utils.ui.UiInfo;

import butterknife.Bind;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import moe.banana.jsonapi2.Document;
import moe.banana.jsonapi2.ObjectDocument;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ereminilya on 24/3/17.
 */

public class RegistrationScreen extends BaseActivity {

    private static final String TAG = "RegistrationScreen";

    @Bind(R.id.email)    EditText uiEmail;
    @Bind(R.id.password) EditText uiPassword;

    @Nullable private ProgressDialog dialog;
    @Nullable private Subscription   registerSubs;

    @NonNull @Override public UiInfo getUiInfo() {
        return new UiInfo(R.layout.screen_registration).enableBackButton()
            .setTitleRes(R.string.title_register);
    }

    @OnClick(R.id.register) void onRegisterClick() {
        dialog = ProgressDialog.show(this, getString(R.string.loading), getString(R.string.registration));
        registerSubs = Deps.api().register(createRegistrationBody())
            .compose(Rxs.doInBackgroundDeliverToUI())
            .doOnTerminate(this::closeDialog)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(result -> {
                Toasty.success(RegistrationScreen.this, getString(R.string.account_has_been_created), Toast.LENGTH_LONG).show();
                finish();
            }, Errors.handle(RegistrationScreen.this));
    }

    private void closeDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override protected void onStop() {
        if (registerSubs != null) {
            registerSubs.unsubscribe();
            registerSubs = null;
        }
        super.onStop();
    }

    @NonNull private Document<User> createRegistrationBody() {
        ObjectDocument<User> userDocument = new ObjectDocument<>();
        userDocument.set(new User(TextViewUtils.textOf(uiEmail), TextViewUtils.textOf(uiPassword)));
        return userDocument;
    }
}