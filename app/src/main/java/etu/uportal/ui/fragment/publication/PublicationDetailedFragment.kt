package etu.uportal.ui.fragment.publication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import etu.uportal.App
import etu.uportal.R
import etu.uportal.Screens
import etu.uportal.model.data.Publication
import etu.uportal.ui.fragment.BaseMvpFragment
import etu.uportal.utils.helpers.click
import kotlinx.android.synthetic.main.fragment_publication_detailed.*

class PublicationDetailedFragment : BaseMvpFragment() {
    val publication: Publication
        get() = arguments?.getSerializable(ARG_PUBLICATION) as Publication

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_publication_detailed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData(publication)
    }

    private fun bindData(publication: Publication) {
        tvPublicationName.text = publication.title
        tvPublicationIntro.text = publication.introText
        tvPublicationCreateDate.text = publication.publishedDate

        val author = publication.authorList.firstOrNull()
        if (author != null) {
            tvAuthorName.text = author.fullName
            tvAuthorName.click {
                App.component.router().navigateTo(Screens.AuthorDetailedFragmentScreen(author).apply { inNewActivity = true })
            }
        } else {
            tvAuthorName.visibility = View.GONE
        }

        val extraFields = publication.extraFieldsStr
        if (extraFields == null) {
            tvPublicationExtraFields.visibility = View.GONE
        } else {
            tvPublicationExtraFields.text = extraFields
        }
    }

    override fun setupToolbar(appBar: AppBarLayout): Toolbar? {
        return provideSimpleToolbar(getString(R.string.publication_info), appBar, false).also {
            appBar.addView(it)
        }
    }

    companion object {
        const val ARG_PUBLICATION = "publication"

        fun newInstance(publication: Publication): PublicationDetailedFragment {
            val args = Bundle().apply { putSerializable(ARG_PUBLICATION, publication) }
            return PublicationDetailedFragment().apply {
                arguments = args
            }
        }
    }
}
