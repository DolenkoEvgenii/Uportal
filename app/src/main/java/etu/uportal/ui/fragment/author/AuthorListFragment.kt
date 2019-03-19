package etu.uportal.ui.fragment.author

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
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
import etu.uportal.utils.pagination.paging
import kotlinx.android.synthetic.main.fragment_author_list.*

class AuthorListFragment : BaseMvpFragment(), AuthorListView {
    @InjectPresenter
    lateinit var presenter: AuthorListPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_author_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshLayout.setOnRefreshListener { presenter.onRefresh() }
        rvAuthors.layoutManager = LinearLayoutManager(context)
    }

    override fun showAuthors(users: List<Author>) {
        val groupAdapter = GroupAdapter<ViewHolder>()
        groupAdapter.addAll(users.map { AuthorItem(it) })
        rvAuthors.adapter = groupAdapter

        rvAuthors.paging({ presenter.loadMore() }, emptyListCount = users.size)
    }

    override fun addAuthors(users: List<Author>) {
        (rvAuthors.adapter as? GroupAdapter)?.addAll(users.map { AuthorItem(it) })
    }

    override fun clearAuthors() {
        (rvAuthors.adapter as? GroupAdapter)?.clear()
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
