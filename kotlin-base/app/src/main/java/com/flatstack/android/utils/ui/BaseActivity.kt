package com.flatstack.android.utils.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import butterknife.Bind
import butterknife.ButterKnife
import com.flatstack.android.R
import com.flatstack.android.utils.Keyboard

abstract class BaseActivity : AppCompatActivity() {

    @Bind(R.id.toolbar) @JvmField var toolbar: View? = null

    abstract val uiInfo: UiInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(uiInfo.layoutRes)
        ButterKnife.bind(this)
        if (intent != null && intent.extras != null) {
            parseArguments(intent.extras)
        }
        if (toolbar != null) {
            setSupportActionBar(toolbar as Toolbar?)
            if (uiInfo.titleRes != 0) {
                setTitle(uiInfo.titleRes)
            } else if (uiInfo.titleStr != null) {
                title = uiInfo.titleStr
            }
            if (uiInfo.hasBackButton) {
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            }
        }
        if (savedInstanceState != null) {
            restoreState(savedInstanceState)
        }
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        ButterKnife.bind(this)
    }

    protected fun restoreState(savedState: Bundle) {}

    protected fun parseArguments(extras: Bundle) {}

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (uiInfo.menuRes != 0) {
            val inflater = menuInflater
            inflater.inflate(uiInfo.menuRes, menu)
            return true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home && uiInfo.hasBackButton) {
            Keyboard.hide(this)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun context(): Context = this
}
