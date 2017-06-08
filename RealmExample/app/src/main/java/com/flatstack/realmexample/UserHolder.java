package com.flatstack.realmexample;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flatstack.realmexample.base.BaseHolder;
import com.flatstack.realmexample.util.Views;

import butterknife.Bind;
import rx.functions.Func1;

public class UserHolder extends BaseHolder<User> {

    @Bind(R.id.tv_name)   TextView  uiNameTv;
    @Bind(R.id.tv_phone)  TextView  uiPhoneTv;
    @Bind(R.id.iv_delete) ImageView uiDeleteIv;

    public UserHolder(View itemView) {
        super(itemView);
    }

    public static Func1<ViewGroup, UserHolder> creator() {
        return viewGroup ->
            new UserHolder(Views.inflate(viewGroup, R.layout.item));
    }

    @Override public void draw(@NonNull User user) {
        uiNameTv.setText(user.getName());
        uiPhoneTv.setText(user.getPhone());
    }

}