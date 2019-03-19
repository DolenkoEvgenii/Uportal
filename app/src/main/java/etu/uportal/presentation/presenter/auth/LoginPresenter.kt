package etu.uportal.presentation.presenter.auth

import com.arellomobile.mvp.InjectViewState
import etu.uportal.App
import etu.uportal.R
import etu.uportal.Screens
import etu.uportal.model.exception.APIException
import etu.uportal.model.network.data.response.AuthResponse
import etu.uportal.model.network.user.UserRepository
import etu.uportal.presentation.presenter.BasePresenter
import etu.uportal.presentation.view.auth.LoginView
import etu.uportal.utils.helpers.getString
import etu.uportal.utils.helpers.showErrorToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class LoginPresenter : BasePresenter<LoginView>() {
    @Inject
    lateinit var userRepository: UserRepository
    @Inject
    lateinit var router: Router

    init {
        App.component.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (userRepository.isAuthorized) {
            refreshToken()
        }
    }

    fun onLoginBtnClicked(login: String, password: String) {
        if (login.isNotBlank()) {
            if (password.isNotBlank()) {
                login(login, password)
            } else {
                showErrorToast(getString(R.string.input_password))
            }
        } else {
            showErrorToast(getString(R.string.input_login))
        }
    }

    private fun login(login: String, password: String) {
        viewState.showLoadingDialog()
        unsubscribeOnDestroy(userRepository
                .login(login, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onUserResponse(it)
                }, {
                    viewState.closeLoadingDialog()
                    if (it is APIException && it.httpCode == 401) {
                        showErrorToast(getString(R.string.invalid_login_or_password))
                    } else {
                        showErrorToast(it.localizedMessage)
                    }
                }))
    }

    private fun refreshToken() {
        viewState.showLoadingDialog()
        unsubscribeOnDestroy(userRepository
                .refreshToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onUserResponse(it)
                }, {
                    userRepository.userPreferences.clearUserData()
                    viewState.closeLoadingDialog()
                }))
    }

    private fun onUserResponse(authResponse: AuthResponse) {
        viewState.closeLoadingDialog()
        router.newRootScreen(Screens.MenuActivityScreen())
    }
}
