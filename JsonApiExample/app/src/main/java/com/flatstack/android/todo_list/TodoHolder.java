package com.flatstack.android.todo_list;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.flatstack.android.R;
import com.flatstack.android.models.ToDo;
import com.flatstack.android.utils.recycler_view.BaseHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ereminilya on 24/3/17.
 */
public class TodoHolder extends BaseHolder<ToDo> {

    @Bind(R.id.title)     TextView uiTitle;
    @Bind(R.id.details)   TextView uiText;
    @Bind(R.id.completed) CheckBox uiStatusCompleted;

    public TodoHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override protected void bind(ToDo item) {
        uiTitle.setText(item.getTitle());
        uiText.setText(item.getText());
        uiStatusCompleted.setChecked(item.isCompleted());
    }
}