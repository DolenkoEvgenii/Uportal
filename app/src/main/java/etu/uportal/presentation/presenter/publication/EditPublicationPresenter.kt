package etu.uportal.presentation.presenter.publication

import com.arellomobile.mvp.InjectViewState
import etu.uportal.App
import etu.uportal.R
import etu.uportal.Screens
import etu.uportal.model.data.Author
import etu.uportal.model.data.Publication
import etu.uportal.model.event.AuthorsPickedEvent
import etu.uportal.model.event.PublicationUpdateEvent
import etu.uportal.model.network.content.ContentRepository
import etu.uportal.presentation.presenter.BasePresenter
import etu.uportal.presentation.view.publication.EditPublicationView
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
class EditPublicationPresenter(val publication: Publication) : BasePresenter<EditPublicationView>() {
    @Inject
    lateinit var contentRepository: ContentRepository
    @Inject
    lateinit var router: Router

    private var selectedAuthors: List<Author> = publication.authorList

    init {
        App.component.inject(this)
        EventBus.getDefault().register(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.bindData(publication.title, publication.introText, publication.publicationFields)
        viewState.showPickedAuthors(publication.authorList.map { it.fullName })
    }

    fun onPickAuthorsClick() {
        router.navigateTo(Screens.SelectAuthorsFragmentScreen().apply { inNewActivity = true })
    }

    fun onCreatePublicationClick(data: EditPublicationData) {
        if (data.title.length < 5) {
            showErrorToast(R.string.title_validation_error)
            return
        }
        if (data.description.length < 10) {
            showErrorToast(R.string.description_validation_error)
            return
        }
        if (selectedAuthors.isEmpty()) {
            showErrorToast(R.string.author_validation_error)
            return
        }

        if (data.fields.any { it.first.isBlank() || it.second.isBlank() }) {
            showErrorToast(R.string.fields_validation_error)
            return
        }

        createPublication(data, selectedAuthors)
    }

    private fun createPublication(data: EditPublicationData, authors: List<Author>) {
        unsubscribeOnDestroy(contentRepository
                .editPublication(publication.id, data, authors, publication.publishedAt.time / 1000)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    showSuccessToast(R.string.successfully)
                    EventBus.getDefault().post(PublicationUpdateEvent())
                    viewState.closeLoadingDialog()
                    router.exit()
                }, {
                    viewState.closeLoadingDialog()
                    showErrorToast(it.localizedMessage)
                }))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAuthorsPicked(event: AuthorsPickedEvent) {
        selectedAuthors = event.authors
        viewState.showPickedAuthors(selectedAuthors.map { it.fullName })
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    class EditPublicationData(val title: String, val description: String, val fields: List<Pair<String, String>>)
}
