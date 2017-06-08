package com.flatstack.realmexample;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.flatstack.realmexample.base.BaseAdapter;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class RecyclerViewAdapter extends BaseAdapter<User, UserHolder>
    implements RealmChangeListener {

    @NonNull private final MainView           view;
    @NonNull private       RealmResults<User> users;

    public RecyclerViewAdapter(MainView view, @NonNull RealmResults<User> users) {
        super(users, UserHolder.creator());
        this.view = view;
        this.users = users;
        users.addChangeListener(this);
    }

    @Override public UserHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        UserHolder userHolder = super.onCreateViewHolder(viewGroup, i);
        userHolder.uiDeleteIv.setOnClickListener(v -> {
            int position = userHolder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                view.deleteUser(users.get(position));
            }
        });
        userHolder.itemView.setOnClickListener(v -> {
            int position = userHolder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                view.changeUser(users.get(position));
            }
        });
        return userHolder;
    }

    @Override public void onChange(Object o) {
        notifyDataSetChanged();
    }

}
