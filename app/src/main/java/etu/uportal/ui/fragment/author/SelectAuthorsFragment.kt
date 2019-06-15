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
import etu.uportal.presentation.presenter.author.SelectAuthorPresenter
import etu.uportal.presentation.view.author.SelectAuthorView
import etu.uportal.ui.adapter.items.SelectAuthorItem
import etu.uportal.ui.fragment.BaseMvpFragment
import etu.uportal.utils.helpers.click
import etu.uportal.utils.pagination.paging
import kotlinx.android.synthetic.main.fragment_author_list.*
import kotlinx.android.synthetic.main.toolbar_accept.view.*

class SelectAuthorsFragment : BaseMvpFragment(), SelectAuthorView {
    @InjectPresenter
    lateinit var presenter: SelectAuthorPresenter

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
        groupAdapter.addAll(users.map { SelectAuthorItem(it, presenter) })
        rvAuthors.adapter = groupAdapter

        rvAuthors.paging({ presenter.loadMore() }, emptyListCount = users.size)
    }

    override fun addAuthors(users: List<Author>) {
        (rvAuthors.adapter as? GroupAdapter)?.addAll(users.map { SelectAuthorItem(it, presenter) })
    }

    override fun clearAuthors() {
        (rvAuthors.adapter as? GroupAdapter)?.clear()
    }

    override fun setupToolbar(appBar: AppBarLayout): Toolbar? {
        return inflateToolbar(R.layout.toolbar_accept, appBar).also {
            it.tvTitle.text = getString(R.string.select_authors)
            it.btAccept.click(this) { presenter.onAcceptClick() }
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
        fun newInstance(): SelectAuthorsFragment {
            return SelectAuthorsFragment()
        }
    }
}
