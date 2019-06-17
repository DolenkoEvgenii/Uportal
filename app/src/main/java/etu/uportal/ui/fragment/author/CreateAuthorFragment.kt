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
import com.google.android.material.appbar.AppBarLayout
import etu.uportal.R
import etu.uportal.presentation.presenter.author.CreateAuthorPresenter
import etu.uportal.presentation.view.author.CreateAuthorView
import etu.uportal.ui.fragment.BaseMvpFragment
import etu.uportal.utils.helpers.children
import etu.uportal.utils.helpers.click
import etu.uportal.utils.helpers.dp
import kotlinx.android.synthetic.main.fragment_manage_author.*

class CreateAuthorFragment : BaseMvpFragment(), CreateAuthorView {
    @InjectPresenter
    lateinit var presenter: CreateAuthorPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_manage_author, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btAddField.click(this) { addExtraFields() }
        btAuthorAction.click(this) { createAuthor() }
    }

    private fun createAuthor() {
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

        val data = CreateAuthorPresenter.CreateAuthorData(
                firstName,
                firstNameEn,
                lastName,
                lastNameEn,
                middleName,
                middleNameEn,
                fields
        )
        presenter.onCreateAuthorClick(data)
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
        return provideSimpleToolbar(getString(R.string.create_author), appBar).also {
            appBar.addView(it)
        }
    }

    companion object {
        fun newInstance(): CreateAuthorFragment {
            return CreateAuthorFragment()
        }
    }
}
