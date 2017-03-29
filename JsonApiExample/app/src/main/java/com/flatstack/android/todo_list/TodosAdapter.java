package com.flatstack.android.todo_list;

import android.view.LayoutInflater;

import com.flatstack.android.R;
import com.flatstack.android.models.ToDo;
import com.flatstack.android.utils.recycler_view.BaseAdapter;
import com.flatstack.android.utils.recycler_view.OnItemClickListener;

import java.util.List;

/**
 * Created by ereminilya on 24/3/17.
 */
class TodosAdapter extends BaseAdapter<ToDo, TodoHolder> {

    TodosAdapter(List<ToDo> toDos, OnItemClickListener<ToDo> itemClickListener) {
        super(toDos, viewGroup -> new TodoHolder(LayoutInflater.from(viewGroup.getContext())
            .inflate(R.layout.item_todo, viewGroup, false)), itemClickListener);
    }
}