package etu.uportal.presentation.presenter.publication

import com.arellomobile.mvp.InjectViewState
import etu.uportal.App
import etu.uportal.model.models.PublicationsModel
import etu.uportal.model.network.content.ContentRepository
import etu.uportal.presentation.presenter.BasePresenter
import etu.uportal.presentation.view.publication.PublicationListView
import etu.uportal.utils.helpers.showErrorToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class PublicationListPresenter : BasePresenter<PublicationListView>() {
    @Inject
    lateinit var contentRepository: ContentRepository

    val model = PublicationsModel

    init {
        App.component.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showPublications(model.publicationList)
    }

    fun loadMore() {
        loadMoreAuthors()
    }

    fun onRefresh() {
        model.clear()
        viewState.clearPublications()
    }

    private fun loadMoreAuthors() {
        if (model.offset == 0) {
            viewState.showLoadingDialog()
        }

        unsubscribeOnDestroy(contentRepository
                .getPublications(model.offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    model.addAll(it.content)
                    viewState.addPublications(it.content)
                    viewState.closeLoadingDialog()
                }, {
                    viewState.closeLoadingDialog()
                    showErrorToast(it.localizedMessage)
                }))
    }
}
