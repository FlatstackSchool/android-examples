package com.flatstack.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.flatstack.android.models.Token;
import com.flatstack.android.models.User;
import com.flatstack.android.todo_list.TodosListScreen;
import com.flatstack.android.utils.Errors;
import com.flatstack.android.utils.Rxs;
import com.flatstack.android.utils.TextViewUtils;
import com.flatstack.android.utils.storage.IStorage;
import com.flatstack.android.utils.ui.BaseActivity;
import com.flatstack.android.utils.ui.UiInfo;

import butterknife.Bind;
import butterknife.OnClick;
import moe.banana.jsonapi2.Document;
import moe.banana.jsonapi2.ObjectDocument;
import rx.Subscription;

import static com.flatstack.android.utils.storage.IStorage.KEY_EMAIL;
import static com.flatstack.android.utils.storage.IStorage.KEY_TOKEN;

/**
 * Created by ereminilya on 24/3/17.
 */

public class SignInScreen extends BaseActivity {

    @Bind(R.id.email)    EditText uiEmail;
    @Bind(R.id.password) EditText uiPassword;

    @Nullable private ProgressDialog dialog;
    @Nullable private Subscription   signingSubs;

    @NonNull @Override public UiInfo getUiInfo() {
        return new UiInfo(R.layout.screen_sign_in).setTitleRes(R.string.title_login);
    }

    @OnClick(R.id.sign_in) void onSignInClick() {
        dialog = ProgressDialog.show(this, getString(R.string.loading), getString(R.string.signing_in));
        signingSubs = Deps.api().signIn(createSignInDoc())
            .compose(Rxs.doInBackgroundDeliverToUI())
            .doOnTerminate(this::closeDialog)
            .subscribe(result -> {
                Token token = result.get();
                IStorage storage = Deps.storage();
                storage.putString(KEY_TOKEN, token.getAuthenticationToken());
                storage.putString(KEY_EMAIL, token.getEmail());
                startActivity(new Intent(this, TodosListScreen.class));
            }, Errors.handle(this));
    }

    private void closeDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @OnClick(R.id.create_account) void onCreateAccountClick() {
        Navigator.createAccount(this);
    }

    @Override protected void onStop() {
        if (signingSubs != null) {
            signingSubs.unsubscribe();
            signingSubs = null;
        }
        super.onStop();
    }

    private Document<User> createSignInDoc() {
        User user = new User(TextViewUtils.textOf(uiEmail), TextViewUtils.textOf(uiPassword));
        ObjectDocument<User> userDocument = new ObjectDocument<>();
        user.setType("sessions");
        userDocument.set(user);
        return userDocument;
    }
}