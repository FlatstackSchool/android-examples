package com.flatstack.android.todo_list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.flatstack.android.Deps;
import com.flatstack.android.Navigator;
import com.flatstack.android.R;
import com.flatstack.android.models.ToDo;
import com.flatstack.android.todo_details.CreateToDoScreen;
import com.flatstack.android.utils.Errors;
import com.flatstack.android.utils.Lists;
import com.flatstack.android.utils.Rxs;
import com.flatstack.android.utils.storage.IStorage;
import com.flatstack.android.utils.ui.BaseActivity;
import com.flatstack.android.utils.ui.UiInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by ereminilya on 24/3/17.
 */

public class TodosListScreen extends BaseActivity {

    private static final int REQUEST_CODE_CREATE_TODO = 5544;

    @Bind(R.id.list)            RecyclerView       uiTodos;
    @Bind(R.id.pull_to_refresh) SwipeRefreshLayout uiPullToRefresh;
    @Bind(R.id.empty)           View               uiEmptyView;

    private List<ToDo> toDos;

    @NonNull @Override public UiInfo getUiInfo() {
        return new UiInfo(R.layout.screen_todos)
            .setTitleRes(R.string.title_todos)
            .setMenuRes(R.menu.menu_with_logout);
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        uiTodos.setLayoutManager(layoutManager);
        if (toDos == null) {
            retrieveTodos();
        }
        uiPullToRefresh.setOnRefreshListener(this::retrieveTodos);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            Deps.storage().remove(IStorage.KEY_EMAIL);
            Deps.storage().remove(IStorage.KEY_TOKEN);
            finish();
            Navigator.signIn(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void retrieveTodos() {
        uiPullToRefresh.setRefreshing(true);
        Deps.api().todos()
            .compose(Rxs.doInBackgroundDeliverToUI())
            .doOnTerminate(() -> uiPullToRefresh.setRefreshing(false))
            .subscribe(result -> {
                toDos = Lists.copyIterator(result.iterator());
                if (toDos.isEmpty()) {
                    uiEmptyView.setVisibility(View.VISIBLE);
                } else {
                    fillAdapter(toDos);
                }
            }, Errors.handle(this));
    }

    private void fillAdapter(List<ToDo> toDos) {
        uiEmptyView.setVisibility(View.GONE);
        uiTodos.setAdapter(new TodosAdapter(toDos, item ->
                Navigator.todoDetails(TodosListScreen.this, item)
            )
        );
    }

    @OnClick(R.id.add) void onAddTodoClick() {
        Navigator.createToDoAndReturnBack(this, REQUEST_CODE_CREATE_TODO);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CREATE_TODO && resultCode == Activity.RESULT_OK) {
            ToDo newlyCreatedToDo = data.getExtras()
                .getParcelable(CreateToDoScreen.KEY_NEWLY_CREATED_TODO);
            if (toDos == null) {
                toDos = new ArrayList<>();
            }
            toDos.add(newlyCreatedToDo);
            fillAdapter(toDos);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}