package com.flatstack.android.main_screen

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import butterknife.Bind
import com.bumptech.glide.Glide
import com.flatstack.android.R
import com.flatstack.android.utils.ui.BaseFragment

class MainFragment : BaseFragment() {

    @Bind(R.id.image) @JvmField var image: ImageView? = null

    override val layoutRes = R.layout.fragment_main

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        image!!.setOnClickListener { listener ->
            TestDialog.show("Example Hello", "Ublyudok, mat' tvoyu, a nu idi syuda, " +
                    "govno sobachye, reshil ko mne lezt'? Ti, zasranec vonyuchiy, mat' tvoyu, a?",
                    fragmentManager)
        }

        Glide.with(this)
                .load("https://pbs.twimg.com/profile_images/502109671600033792/QOAC0YGo.png")
                .into(image!!)
    }
}
