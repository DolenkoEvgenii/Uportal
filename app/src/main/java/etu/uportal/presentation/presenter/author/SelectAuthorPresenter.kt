package etu.uportal.presentation.presenter.author

import com.arellomobile.mvp.InjectViewState
import etu.uportal.App
import etu.uportal.model.data.Author
import etu.uportal.model.event.AuthorsPickedEvent
import etu.uportal.model.models.AuthorsModel
import etu.uportal.model.network.content.ContentRepository
import etu.uportal.presentation.presenter.BasePresenter
import etu.uportal.presentation.view.author.SelectAuthorView
import etu.uportal.ui.adapter.items.SelectAuthorItem
import etu.uportal.utils.helpers.showErrorToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class SelectAuthorPresenter : BasePresenter<SelectAuthorView>(), SelectAuthorItem.SelectAuthorDelegate {
    @Inject
    lateinit var contentRepository: ContentRepository
    @Inject
    lateinit var router: Router

    val model = AuthorsModel
    private val selectedAuthors: MutableList<Author> = ArrayList()

    init {
        App.component.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showAuthors(model.authorList)
    }

    override fun onAuthorClicked(author: Author) {
        if (selectedAuthors.contains(author)) {
            selectedAuthors.remove(author)
        } else {
            selectedAuthors.add(author)
        }
    }

    override fun isAuthorSelected(author: Author): Boolean {
        return selectedAuthors.contains(author)
    }

    fun loadMore() {
        loadMoreAuthors()
    }

    fun onRefresh() {
        model.clear()
        viewState.clearAuthors()
    }

    fun onAcceptClick() {
        router.exit()
        EventBus.getDefault().post(AuthorsPickedEvent(selectedAuthors))
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
