package com.flatstack.android.todo_details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.widget.CheckBox;
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

public class UpdateToDoScreen extends BaseActivity {

    private static final String KEY_TODO = "toDo";

    public static Intent newInstance(@NonNull Context context, @NonNull ToDo toDo) {
        Intent intent = new Intent(context, UpdateToDoScreen.class);
        intent.putExtra(KEY_TODO, (Parcelable) toDo);
        return intent;
    }

    @Bind(R.id.title)       EditText uiTitle;
    @Bind(R.id.description) EditText uiDescription;
    @Bind(R.id.completed)   CheckBox uiCompletedCb;

    private ToDo todoToUpdate;

    @NonNull @Override public UiInfo getUiInfo() {
        return new UiInfo(R.layout.screen_todo_details).enableBackButton()
            .setTitleRes(R.string.title_edit_todo);
    }

    @Override protected void parseArguments(@NonNull Bundle extras) {
        todoToUpdate = extras.getParcelable(KEY_TODO);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiTitle.setText(todoToUpdate.getTitle());
        uiDescription.setText(todoToUpdate.getText());
        uiCompletedCb.setChecked(todoToUpdate.isCompleted());
    }

    @OnClick(R.id.save) void onSaveClick() {
        Deps.api().updateToDo(todoToUpdate.getId(), prepareRequest())
            .compose(Rxs.doInBackgroundDeliverToUI())
            .subscribe(result -> {
                Toasty.success(this, getString(R.string.to_do_has_been_updated)).show();
                finish();
            }, Errors.handle(this));
    }

    @NonNull private ObjectDocument<ToDo> prepareRequest() {
        ToDo toDo = new ToDo(TextViewUtils.textOf(uiTitle), TextViewUtils.textOf(uiDescription));
        toDo.setId(todoToUpdate.getId());
        toDo.setCompleted(uiCompletedCb.isChecked());
        ObjectDocument<ToDo> doc = new ObjectDocument<>();
        doc.set(toDo);
        return doc;
    }
}