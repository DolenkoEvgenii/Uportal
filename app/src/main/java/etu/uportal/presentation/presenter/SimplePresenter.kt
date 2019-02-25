package etu.uportal.presentation.presenter

import etu.uportal.App
import etu.uportal.presentation.view.BaseMvpView
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppScreen
import javax.inject.Inject

class SimplePresenter constructor(
        private val routeScreen: SupportAppScreen,
        private val isRoot: Boolean) : BasePresenter<BaseMvpView>() {

    @Inject
    lateinit var router: Router

    init {
        App.component.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (isRoot) {
            router.newRootScreen(routeScreen)
        } else {
            router.navigateTo(routeScreen)
        }
    }
}
