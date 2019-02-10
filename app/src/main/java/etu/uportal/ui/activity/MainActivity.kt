package etu.uportal.ui.activity

import android.os.Bundle

import etu.uportal.R
import etu.uportal.ui.activity.base.BaseMvpFragmentActivity

class MainActivity : BaseMvpFragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
