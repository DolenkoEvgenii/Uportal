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

    fun onSearch(query: String) {
        model.searchQuery = query

        if (query.isBlank()) {
            onRefresh()
        } else {
            val wasEmpty = model.isEmpty
            model.clear()
            viewState.clearPublications()
            viewState.showLoadingDialog()

            if (wasEmpty) {
                loadMore()
            }
        }
    }

    fun loadMore() {
        loadMoreAuthors()
    }

    fun onRefresh() {
        val wasEmpty = model.isEmpty
        model.clear()
        viewState.clearPublications()

        if (wasEmpty) {
            loadMore()
        }
    }

    private fun loadMoreAuthors() {
        if (model.offset > 0 && model.searchQuery.isNotBlank()) {
            viewState.closeLoadingDialog()
            return
        }

        if (model.offset == 0) {
            viewState.showLoadingDialog()
        }

        unsubscribeOnDestroy(contentRepository
                .getPublications(model.offset, model.searchQuery)
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
