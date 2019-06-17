package etu.uportal.presentation.presenter.author

import com.arellomobile.mvp.InjectViewState
import etu.uportal.App
import etu.uportal.R
import etu.uportal.model.data.Author
import etu.uportal.model.event.AuthorUpdateEvent
import etu.uportal.model.network.content.ContentRepository
import etu.uportal.presentation.presenter.BasePresenter
import etu.uportal.presentation.view.author.EditAuthorView
import etu.uportal.utils.helpers.showErrorToast
import etu.uportal.utils.helpers.showSuccessToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class EditAuthorPresenter(val author: Author) : BasePresenter<EditAuthorView>() {
    @Inject
    lateinit var contentRepository: ContentRepository
    @Inject
    lateinit var router: Router

    init {
        App.component.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.bindData(author)
    }

    fun onEditAuthorClick(data: EditAuthorData) {
        if (data.firstName.length < 5 || data.firstNameEn.length < 5) {
            showErrorToast(R.string.name_validation_error)
            return
        }
        if (data.lastName.length < 5 || data.lastNameEn.length < 5) {
            showErrorToast(R.string.lastname_validation_error)
            return
        }

        if (data.fields.any { it.first.isBlank() || it.second.isBlank() }) {
            showErrorToast(R.string.fields_validation_error)
            return
        }

        editAuthor(data)
    }

    private fun editAuthor(data: EditAuthorData) {
        unsubscribeOnDestroy(contentRepository
                .editAuthor(author.id, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    showSuccessToast(R.string.successfully)
                    EventBus.getDefault().post(AuthorUpdateEvent())
                    viewState.closeLoadingDialog()
                    router.exit()
                }, {
                    viewState.closeLoadingDialog()
                    showErrorToast(it.localizedMessage)
                }))
    }

    class EditAuthorData(val firstName: String,
                         val firstNameEn: String,
                         val lastName: String,
                         val lastNameEn: String,
                         val middleName: String,
                         val middleNameEn: String,
                         val fields: List<Pair<String, String>>)
}
