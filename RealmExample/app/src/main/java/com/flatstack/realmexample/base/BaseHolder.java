package com.flatstack.realmexample.base;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseHolder<T> extends RecyclerView.ViewHolder implements CanDraw<T> {

    protected List<T> items;
    protected int     position;
    protected T       item;

    public BaseHolder(View itemView) {
        super(itemView);
        if (needViewInject()) {
            ButterKnife.bind(this, itemView);
        }
    }

    public T getItem() {
        return item;
    }

    public void setData(List<T> items, int position) {
        this.items = items;
        this.position = position;
        this.item = items.get(position);
    }

    protected Context getContext() {
        return itemView.getContext();
    }

    protected String getString(@StringRes int str, Object... args) {
        return getContext().getString(str, args);
    }

    protected boolean needViewInject() {
        return true;
    }
}