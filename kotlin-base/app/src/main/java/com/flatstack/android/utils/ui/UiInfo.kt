package com.flatstack.android.utils.ui

import android.support.annotation.LayoutRes
import android.support.annotation.MenuRes
import android.support.annotation.StringRes

class UiInfo(@param:LayoutRes val layoutRes: Int, @param:StringRes val titleRes: Int = 0,
             @param:MenuRes val menuRes: Int = 0, val titleStr: String? = null,
             val hasBackButton: Boolean = false)
