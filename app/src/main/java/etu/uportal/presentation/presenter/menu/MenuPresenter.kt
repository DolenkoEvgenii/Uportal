package etu.uportal.presentation.presenter.menu

import com.arellomobile.mvp.InjectViewState
import etu.uportal.App
import etu.uportal.Screens
import etu.uportal.model.preference.UserPreferences
import etu.uportal.presentation.presenter.BasePresenter
import etu.uportal.presentation.view.menu.MenuView
import etu.uportal.ui.activity.menu.MenuActivity
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MenuPresenter : BasePresenter<MenuView>() {
    @Inject
    lateinit var userPreferences: UserPreferences
    @Inject
    lateinit var router: Router

    init {
        App.component.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        onDrawerItemClick(MenuActivity.TAB_PUBLICATIONS)
    }

    fun onDrawerItemClick(id: Long) {
        viewState.selectDrawerItemWithId(id)

        when (id) {
            MenuActivity.TAB_USERS -> router.newRootScreen(Screens.UserListFragmentScreen())
            MenuActivity.TAB_AUTHORS -> router.newRootScreen(Screens.AuthorListFragmentScreen())
            MenuActivity.TAB_PUBLICATIONS -> router.newRootScreen(Screens.PublicationListFragmentScreen())
            MenuActivity.TAB_LOGOUT -> logout()
        }
    }

    private fun logout() {
        userPreferences.clearUserData()
        router.newRootScreen(Screens.AuthActivityScreen())
    }
}
