package etu.uportal.ui.fragment.author

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Space
import androidx.appcompat.widget.Toolbar
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.appbar.AppBarLayout
import etu.uportal.R
import etu.uportal.model.data.Author
import etu.uportal.presentation.presenter.author.EditAuthorPresenter
import etu.uportal.presentation.view.author.EditAuthorView
import etu.uportal.ui.fragment.BaseMvpFragment
import etu.uportal.utils.helpers.children
import etu.uportal.utils.helpers.click
import etu.uportal.utils.helpers.dp
import kotlinx.android.synthetic.main.fragment_manage_author.*

class EditAuthorFragment : BaseMvpFragment(), EditAuthorView {
    @InjectPresenter
    lateinit var presenter: EditAuthorPresenter

    val author: Author
        get() = arguments?.getSerializable(ARG_AUTHOR) as Author

    @ProvidePresenter
    fun providePresenter(): EditAuthorPresenter {
        return EditAuthorPresenter(author)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_manage_author, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btAddField.click(this) { addExtraFields() }

        btAuthorAction.setText(R.string.update_author)
        btAuthorAction.click(this) { editAuthor() }
    }

    override fun bindData(author: Author) {
        etFirstName.setText(author.firstName)
        etFirstNameEn.setText(author.firstNameEn)
        etLastName.setText(author.lastName)
        etLastNameEn.setText(author.lastNameEn)
        etMiddleName.setText(author.middleName)
        etMiddleNameEn.setText(author.middleNameEn)

        vExtraFieldsContainer.removeAllViewsInLayout()
        author.fields.forEach { addExtraFields(it.name, it.value) }
    }

    private fun editAuthor() {
        val firstName = etFirstName.text.toString()
        val firstNameEn = etFirstNameEn.text.toString()
        val lastName = etLastName.text.toString()
        val lastNameEn = etLastNameEn.text.toString()
        val middleName = etMiddleName.text.toString()
        val middleNameEn = etMiddleNameEn.text.toString()

        val fields: MutableList<Pair<String, String>> = ArrayList()
        vExtraFieldsContainer.children.filter { it is ViewGroup }.forEach { child ->
            val name = child.findViewById<EditText>(R.id.etName).text.toString()
            val value = child.findViewById<EditText>(R.id.etValue).text.toString()
            fields.add(Pair(name, value))
        }

        val data = EditAuthorPresenter.EditAuthorData(
                firstName,
                firstNameEn,
                lastName,
                lastNameEn,
                middleName,
                middleNameEn,
                fields
        )
        presenter.onEditAuthorClick(data)
    }

    private fun addExtraFields(title: String = "", value: String = "") {
        vExtraFieldsContainer.addView(getSpace(15.dp))

        val extraFieldsView = layoutInflater.inflate(R.layout.view_extra_fields, vExtraFieldsContainer, false)
        extraFieldsView.findViewById<EditText>(R.id.etName).setText(title)
        extraFieldsView.findViewById<EditText>(R.id.etValue).setText(value)
        vExtraFieldsContainer.addView(extraFieldsView)
    }

    private fun getSpace(height: Int): View {
        return Space(context).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height)
        }
    }

    override fun setupToolbar(appBar: AppBarLayout): Toolbar? {
        return provideSimpleToolbar(getString(R.string.update_author), appBar).also {
            appBar.addView(it)
        }
    }

    companion object {
        private const val ARG_AUTHOR = "author arg"

        fun newInstance(author: Author): EditAuthorFragment {
            val args = Bundle().apply { putSerializable(ARG_AUTHOR, author) }
            return EditAuthorFragment().apply { arguments = args }
        }
    }
}
