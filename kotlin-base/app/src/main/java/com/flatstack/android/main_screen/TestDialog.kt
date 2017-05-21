package com.flatstack.android.main_screen

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.View
import android.widget.TextView
import butterknife.Bind
import com.flatstack.android.R
import com.flatstack.android.utils.ui.BaseDialogFragment

class TestDialog : BaseDialogFragment() {

    @Bind(R.id.dialog_title) @JvmField var uiTitle: TextView? = null
    @Bind(R.id.dialog_message) @JvmField var uiMessage: TextView? = null

    private var title: String? = null
    private var message: String? = null

    override val layoutRes = R.layout.dialog_test

    override fun parseArguments(args: Bundle) {
        title = arguments.getString(KEY_TITLE)
        message = arguments.getString(KEY_MESSAGE)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiTitle!!.text = title ?: ""
        uiMessage!!.text = message ?: ""
    }

    companion object {

        private val KEY_TITLE = "dialogTitle"
        private val KEY_MESSAGE = "dialogMessage"

        fun show(title: String?, message: String?, fm: FragmentManager) {

            val dialog = TestDialog()

            val args = Bundle()
            args.putString(KEY_TITLE, title ?: "")
            args.putString(KEY_MESSAGE, message ?: "")
            dialog.arguments = args

            dialog.show(fm, TestDialog::class.java.name)
        }
    }
}
