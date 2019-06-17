package etu.uportal.presentation.presenter.author

import com.arellomobile.mvp.InjectViewState
import etu.uportal.App
import etu.uportal.R
import etu.uportal.Screens
import etu.uportal.model.data.Author
import etu.uportal.model.event.AuthorUpdateEvent
import etu.uportal.model.models.AuthorsModel
import etu.uportal.model.network.content.ContentRepository
import etu.uportal.presentation.presenter.BasePresenter
import etu.uportal.presentation.view.author.AuthorListView
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
class AuthorListPresenter : BasePresenter<AuthorListView>() {
    @Inject
    lateinit var contentRepository: ContentRepository
    @Inject
    lateinit var router: Router

    val model = AuthorsModel

    init {
        App.component.inject(this)
        EventBus.getDefault().register(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showAuthors(model.authorList)
    }

    fun onCreateAuthorClick() {
        router.navigateTo(Screens.CreateAuthorFragmentScreen().apply { inNewActivity = true })
    }

    fun onAuthorEditClick(author: Author) {
        router.navigateTo(Screens.EditAuthorFragmentScreen(author).apply { inNewActivity = true })
    }

    fun onAuthorDeleteClick(author: Author) {
        if (author.allowDelete) {
            deleteAuthor(author.id)
        } else {
            showErrorToast(R.string.cant_delete_author)
        }
    }

    fun loadMore() {
        loadMoreAuthors()
    }

    fun onRefresh() {
        model.clear()
        viewState.clearAuthors()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onUpdateEvent(event: AuthorUpdateEvent) {
        onRefresh()
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
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

    private fun deleteAuthor(authorId: Int) {
        viewState.showLoadingDialog()

        unsubscribeOnDestroy(contentRepository
                .deleteAuthor(authorId)
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
}
