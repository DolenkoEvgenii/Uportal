package etu.uportal.presentation.presenter.publication

import com.arellomobile.mvp.InjectViewState
import etu.uportal.App
import etu.uportal.R
import etu.uportal.Screens
import etu.uportal.model.data.Publication
import etu.uportal.model.event.PublicationUpdateEvent
import etu.uportal.model.models.PublicationsModel
import etu.uportal.model.network.content.ContentRepository
import etu.uportal.presentation.presenter.BasePresenter
import etu.uportal.presentation.view.publication.PublicationListView
import etu.uportal.utils.helpers.showErrorToast
import etu.uportal.utils.helpers.showSuccessToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class PublicationListPresenter : BasePresenter<PublicationListView>() {
    @Inject
    lateinit var contentRepository: ContentRepository
    @Inject
    lateinit var router: Router

    val model = PublicationsModel

    init {
        App.component.inject(this)
        EventBus.getDefault().register(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showPublications(model.publicationList)
    }

    fun onCreatePublicationClick() {
        router.navigateTo(Screens.CreatePublicationFragmentScreen().apply { inNewActivity = true })
    }

    fun onPublicationEditClick(publication: Publication) {
        router.navigateTo(Screens.EditPublicationFragmentScreen(publication).apply { inNewActivity = true })
    }

    fun onPublicationDeleteClick(publication: Publication) {
        deletePublication(publication.id)
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

    private fun deletePublication(publicationId: Int) {
        viewState.showLoadingDialog()

        unsubscribeOnDestroy(contentRepository
                .deletePublication(publicationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    showSuccessToast(R.string.successfully)
                    onRefresh()
                }, {
                    viewState.closeLoadingDialog()
                    showErrorToast(it.localizedMessage)
                }))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onUpdateEvent(event: PublicationUpdateEvent) {
        onRefresh()
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}
