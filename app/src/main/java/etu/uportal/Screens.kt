package etu.uportal

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import etu.uportal.model.navigator.BaseAppScreen
import etu.uportal.ui.activity.MainActivity
import etu.uportal.ui.activity.auth.AuthActivity
import etu.uportal.ui.fragment.auth.LoginFragment

object Screens {
    class MainActivityScreen : BaseAppScreen() {
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    class AuthActivityScreen : BaseAppScreen() {
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, AuthActivity::class.java)
        }
    }

    class LoginFragmentScreen : BaseAppScreen() {
        override fun getFragment(): Fragment {
            return LoginFragment.newInstance()
        }
    }
}
