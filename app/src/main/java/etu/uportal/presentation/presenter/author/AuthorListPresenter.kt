package etu.uportal.presentation.presenter.author

import com.arellomobile.mvp.InjectViewState
import etu.uportal.App
import etu.uportal.model.models.AuthorsModel
import etu.uportal.model.network.content.ContentRepository
import etu.uportal.presentation.presenter.BasePresenter
import etu.uportal.presentation.view.author.AuthorListView
import etu.uportal.utils.helpers.showErrorToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class AuthorListPresenter : BasePresenter<AuthorListView>() {
    @Inject
    lateinit var contentRepository: ContentRepository

    val model = AuthorsModel

    init {
        App.component.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showAuthors(model.authorList)
    }

    fun loadMore(){
        loadMoreAuthors()
    }

    fun onRefresh() {
        model.clear()
        viewState.clearAuthors()
    }

    private fun loadMoreAuthors() {
        if (model.offset == 0) {
            viewState.showLoadingDialog()
        }

        unsubscribeOnDestroy(contentRepository
                .getAuthors(model.offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    model.addAll(it.content)
                    viewState.addAuthors(it.content)
                    viewState.closeLoadingDialog()
                }, {
                    viewState.closeLoadingDialog()
                    showErrorToast(it.localizedMessage)
                }))
    }
}
