package etu.uportal.ui.activity.base

import etu.uportal.presentation.presenter.SimplePresenter
import ru.terrakok.cicerone.android.support.SupportAppScreen

class SimpleMvpRootActivity : BaseMvpRootActivity() {
    override fun providePresenter(): SimplePresenter {
        return SimplePresenter(intent.getSerializableExtra(SCREEN_ARG) as SupportAppScreen, true)
    }

    companion object {
        const val SCREEN_ARG = "screen_arg"
    }
}