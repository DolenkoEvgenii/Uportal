package etu.uportal.ui.fragment.author

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.appbar.AppBarLayout
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import etu.uportal.R
import etu.uportal.model.data.Author
import etu.uportal.presentation.presenter.author.AuthorListPresenter
import etu.uportal.presentation.view.author.AuthorListView
import etu.uportal.ui.adapter.items.AuthorItem
import etu.uportal.ui.fragment.BaseMvpFragment
import etu.uportal.utils.helpers.click
import etu.uportal.utils.pagination.paging
import kotlinx.android.synthetic.main.fragment_author_list.*

class AuthorListFragment : BaseMvpFragment(), AuthorListView, AuthorItem.OnAuthorClickListener {
    @InjectPresenter
    lateinit var presenter: AuthorListPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_author_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshLayout.setOnRefreshListener { presenter.onRefresh() }
        rvAuthors.layoutManager = LinearLayoutManager(context)
        btCreateAuthor.click(this) { presenter.onCreateAuthorClick() }
    }

    override fun showAuthors(users: List<Author>) {
        val groupAdapter = GroupAdapter<ViewHolder>()
        groupAdapter.addAll(users.map { AuthorItem(it, this) })
        rvAuthors.adapter = groupAdapter

        rvAuthors.paging({ presenter.loadMore() }, emptyListCount = users.size)
    }

    override fun addAuthors(users: List<Author>) {
        (rvAuthors.adapter as? GroupAdapter)?.addAll(users.map { AuthorItem(it, this) })
    }

    override fun clearAuthors() {
        (rvAuthors.adapter as? GroupAdapter)?.clear()
    }

    override fun onLongClick(author: Author) {
        val context = context ?: return

        MaterialDialog(context).show {
            listItems(items = listOf(getString(R.string.edit), getString(R.string.delete))) { dialog, index, text ->
                when (index) {
                    0 -> presenter.onAuthorEditClick(author)
                    1 -> presenter.onAuthorDeleteClick(author)
                }
            }
        }
    }

    override fun setupToolbar(appBar: AppBarLayout): Toolbar? {
        return provideSimpleToolbar(getString(R.string.authors), appBar).also {
            appBar.addView(it)
        }
    }

    override fun showLoadingDialog() {
        refreshLayout.isRefreshing = true
    }

    override fun closeLoadingDialog() {
        refreshLayout.isRefreshing = false
    }

    companion object {
        fun newInstance(): AuthorListFragment {
            return AuthorListFragment()
        }
    }
}
