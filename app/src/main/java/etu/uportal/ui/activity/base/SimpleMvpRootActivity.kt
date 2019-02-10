package etu.uportal.ui.activity.base

import ru.terrakok.cicerone.android.support.SupportAppScreen
import etu.uportal.presentation.presenter.SimplePresenter

class SimpleMvpRootActivity : BaseMvpRootActivity() {
    override fun providePresenter(): SimplePresenter {
        return SimplePresenter(intent.getSerializableExtra(SCREEN_ARG) as SupportAppScreen, true)
    }

    companion object {
        const val SCREEN_ARG = "screen_arg"
    }
}