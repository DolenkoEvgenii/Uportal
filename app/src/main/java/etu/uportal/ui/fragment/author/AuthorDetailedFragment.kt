package etu.uportal.ui.fragment.author

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.trello.rxlifecycle3.android.FragmentEvent
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import etu.uportal.App
import etu.uportal.R
import etu.uportal.model.data.AuthorDetailed
import etu.uportal.model.data.Publication
import etu.uportal.ui.adapter.items.PublicationItem
import etu.uportal.ui.fragment.BaseMvpFragment
import etu.uportal.utils.helpers.showErrorToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_author_detailed.*

class AuthorDetailedFragment : BaseMvpFragment(), PublicationItem.OnPublicationClickListener {
    private val authorId: Int
        get() = arguments?.getInt(ARG_AUTHOR_ID) ?: throw  IllegalArgumentException()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_author_detailed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadAuthorDetailed()
    }

    @SuppressLint("CheckResult")
    private fun loadAuthorDetailed() {
        showLoadingDialog()

        App.component.contentRepository()
                .getAuthorDetailed(authorId)
                .compose(bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    bindData(it)
                    closeLoadingDialog()
                }, {
                    closeLoadingDialog()
                    showErrorToast(it.localizedMessage)
                    App.component.router().exit()
                })
    }

    private fun bindData(author: AuthorDetailed) {
        tvAuthorName.text = author.fullName
        tvAuthorEngName.text = author.fullNameEng

        val extraFields = author.extraFieldsStr
        if (extraFields == null) {
            tvAuthorExtraFields.visibility = View.GONE
        } else {
            tvAuthorExtraFields.text = extraFields
        }

        rvPublications.layoutManager = LinearLayoutManager(context)
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(author.publications.map { PublicationItem(it, this@AuthorDetailedFragment) })
        }
        rvPublications.adapter = groupAdapter
    }

    override fun onLongClick(publication: Publication) {

    }

    override fun setupToolbar(appBar: AppBarLayout): Toolbar? {
        return provideSimpleToolbar(getString(R.string.author_info), appBar, false).also {
            appBar.addView(it)
        }
    }

    companion object {
        private const val ARG_AUTHOR_ID = "author id"

        fun newInstance(authorId: Int): AuthorDetailedFragment {
            val args = Bundle().apply { putInt(ARG_AUTHOR_ID, authorId) }
            return AuthorDetailedFragment().apply {
                arguments = args
            }
        }
    }
}
