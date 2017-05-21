package com.flatstack.android.utils.recycler_view

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by ereminilya on 14/12/16.
 */
abstract class BaseHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: T)
}