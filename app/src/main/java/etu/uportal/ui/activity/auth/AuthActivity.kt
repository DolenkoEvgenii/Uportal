package etu.uportal.ui.activity.auth

import android.os.Bundle
import android.view.View
import com.google.android.material.appbar.AppBarLayout
import etu.uportal.R
import etu.uportal.Screens
import etu.uportal.presentation.presenter.SimplePresenter
import etu.uportal.ui.activity.base.BaseMvpRootActivity


class AuthActivity : BaseMvpRootActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setContentView(R.layout.activity_auth)
    }

    override fun providePresenter(): SimplePresenter {
        return SimplePresenter(Screens.LoginFragmentScreen(), true)
    }

    override fun provideAppBar(): AppBarLayout? {
        return null
    }
}
