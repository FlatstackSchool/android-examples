package com.flatstack.android.utils.ui

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife

abstract class BaseFragment : Fragment() {

    @get:LayoutRes abstract val layoutRes: Int

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)
        if (savedState != null) {
            restoreState(savedState)
        }
    }

    fun restoreState(savedState: Bundle) {}

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(layoutRes, container, false)
        ButterKnife.bind(this, rootView)
        return rootView
    }
}
