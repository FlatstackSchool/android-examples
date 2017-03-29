package com.flatstack.android.todo_details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

import com.flatstack.android.Deps;
import com.flatstack.android.R;
import com.flatstack.android.models.ToDo;
import com.flatstack.android.utils.Errors;
import com.flatstack.android.utils.Rxs;
import com.flatstack.android.utils.TextViewUtils;
import com.flatstack.android.utils.ui.BaseActivity;
import com.flatstack.android.utils.ui.UiInfo;

import butterknife.Bind;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import moe.banana.jsonapi2.ObjectDocument;

/**
 * Created by ereminilya on 29/3/17.
 */
public class CreateToDoScreen extends BaseActivity {

    public static final String KEY_NEWLY_CREATED_TODO = "newlyCreatedToDo";
    @Bind(R.id.title)       EditText uiTitle;
    @Bind(R.id.description) EditText uiDescription;

    @NonNull @Override public UiInfo getUiInfo() {
        return new UiInfo(R.layout.screen_todo_details).enableBackButton()
            .setTitleRes(R.string.title_create_todo);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.completed).setVisibility(View.GONE);
    }

    @OnClick(R.id.save) void onSaveClick() {
        ToDo toDo = new ToDo(TextViewUtils.textOf(uiTitle), TextViewUtils.textOf(uiDescription));
        ObjectDocument<ToDo> document = new ObjectDocument<>();
        document.set(toDo);
        Deps.api().createToDo(document)
            .compose(Rxs.doInBackgroundDeliverToUI())
            .subscribe(result -> {
                Toasty.success(CreateToDoScreen.this, getString(R.string.todo_has_been_created)).show();
                Intent intent = new Intent();
                intent.putExtra(KEY_NEWLY_CREATED_TODO, (Parcelable) toDo);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }, Errors.handle(this));
    }
}