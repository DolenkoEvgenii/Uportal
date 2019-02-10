package etu.uportal

import android.content.Context
import android.content.Intent
import etu.uportal.model.navigator.BaseAppScreen
import etu.uportal.ui.activity.MainActivity

object Screens {
    class MainActivityScreen : BaseAppScreen() {
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
