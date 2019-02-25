package etu.uportal.model.navigator

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import etu.uportal.ui.activity.base.SimpleMvpRootActivity
import ru.terrakok.cicerone.android.support.SupportAppScreen
import java.io.Serializable

// Базовый класс для всех экранов
// Если стоит флаг открыть в новом окне, то подменяет интент на базовую активночть, и отдает туда новый экран с заданным фрагментом
open class BaseAppScreen : SupportAppScreen() {
    open var inNewActivity = false

    override fun getActivityIntent(context: Context?): Intent? {
        val intent = super.getActivityIntent(context)
        if (intent == null && inNewActivity) {
            if (this !is Serializable) throw RuntimeException("${this.screenKey} should implement serializable")

            return Intent(context, SimpleMvpRootActivity::class.java).apply {
                putExtra(SimpleMvpRootActivity.SCREEN_ARG, FragmentAppScreen { fragment })
            }
        }

        return intent
    }

    // Экран обертка с лямбдой, возвращающей фрагмент
    private class FragmentAppScreen(val fragmentProvider: () -> Fragment) : SupportAppScreen(), Serializable {
        override fun getFragment(): Fragment {
            return fragmentProvider()
        }
    }
}