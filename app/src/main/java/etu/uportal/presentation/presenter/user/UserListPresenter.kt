package etu.uportal.presentation.presenter.user

import com.arellomobile.mvp.InjectViewState
import etu.uportal.App
import etu.uportal.model.models.UsersModel
import etu.uportal.model.network.content.ContentRepository
import etu.uportal.presentation.presenter.BasePresenter
import etu.uportal.presentation.view.user.UserListView
import etu.uportal.utils.helpers.showErrorToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class UserListPresenter : BasePresenter<UserListView>() {
    @Inject
    lateinit var contentRepository: ContentRepository

    val model = UsersModel

    init {
        App.component.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showUsers(model.userList)
    }

    fun loadMore(){
        loadMoreUsers()
    }

    fun onRefresh() {
        model.clear()
        viewState.clearUsers()
    }

    private fun loadMoreUsers() {
        if (model.offset == 0) {
            viewState.showLoadingDialog()
        }

        unsubscribeOnDestroy(contentRepository
                .getUsers(model.offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    model.addAll(it.content)
                    viewState.addUsers(it.content)
                    viewState.closeLoadingDialog()
                }, {
                    viewState.closeLoadingDialog()
                    showErrorToast(it.localizedMessage)
                }))
    }
}
