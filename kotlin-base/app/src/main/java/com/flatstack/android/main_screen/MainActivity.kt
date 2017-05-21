package com.flatstack.android.main_screen

import android.os.Bundle

import com.flatstack.android.utils.ui.BaseActivity
import com.flatstack.android.R
import com.flatstack.android.utils.ui.UiInfo

class MainActivity : BaseActivity() {

    override val uiInfo = UiInfo(layoutRes = R.layout.activity_main, titleRes = R.string.app_name,
            hasBackButton = true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.content, MainFragment())
                    .commit()
        }
    }
}