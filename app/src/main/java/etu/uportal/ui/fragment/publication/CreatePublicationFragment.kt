package etu.uportal.ui.fragment.publication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Space
import androidx.appcompat.widget.Toolbar
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.appbar.AppBarLayout
import etu.uportal.R
import etu.uportal.presentation.presenter.publication.CreatePublicationPresenter
import etu.uportal.presentation.view.publication.CreatePublicationView
import etu.uportal.ui.fragment.BaseMvpFragment
import etu.uportal.utils.helpers.children
import etu.uportal.utils.helpers.click
import etu.uportal.utils.helpers.dp
import kotlinx.android.synthetic.main.fragment_create_publication.*

class CreatePublicationFragment : BaseMvpFragment(), CreatePublicationView {
    @InjectPresenter
    lateinit var presenter: CreatePublicationPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_publication, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btSelectAuthors.click(this) { presenter.onPickAuthorsClick() }
        btAddField.click(this) { addExtraFields() }
        btCreatePublication.click(this) { createPublication() }
    }

    override fun showPickedAuthors(authors: List<String>) {
        if (authors.isEmpty()) {
            tvSelectedAuthors.setText(R.string.empty)
        } else {
            tvSelectedAuthors.text = authors.joinToString("\n")
        }
    }

    private fun createPublication() {
        val title = etTitle.text.toString()
        val description = etDescription.text.toString()

        val fields: MutableList<Pair<String, String>> = ArrayList()
        vExtraFieldsContainer.children.filter { it is ViewGroup }.forEach { child ->
            val name = child.findViewById<EditText>(R.id.etName).text.toString()
            val value = child.findViewById<EditText>(R.id.etValue).text.toString()
            fields.add(Pair(name, value))
        }

        presenter.onCreatePublicationClick(CreatePublicationPresenter.CreatePublicationData(title, description, fields))
    }

    private fun addExtraFields() {
        vExtraFieldsContainer.addView(getSpace(15.dp))

        val extraFieldsView = layoutInflater.inflate(R.layout.view_extra_fields, vExtraFieldsContainer, false)
        vExtraFieldsContainer.addView(extraFieldsView)
    }

    private fun getSpace(height: Int): View {
        return Space(context).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height)
        }
    }

    override fun setupToolbar(appBar: AppBarLayout): Toolbar? {
        return provideSimpleToolbar(getString(R.string.create_publication), appBar).also {
            appBar.addView(it)
        }
    }

    companion object {
        fun newInstance(): CreatePublicationFragment {
            return CreatePublicationFragment()
        }
    }
}
