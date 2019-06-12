package etu.uportal.ui.fragment.publication

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
import etu.uportal.model.data.Publication
import etu.uportal.presentation.presenter.publication.PublicationListPresenter
import etu.uportal.presentation.view.publication.PublicationListView
import etu.uportal.ui.adapter.items.PublicationItem
import etu.uportal.ui.fragment.BaseMvpFragment
import etu.uportal.utils.pagination.paging
import kotlinx.android.synthetic.main.fragment_publication_list.*
import kotlinx.android.synthetic.main.toolbar_simple.view.*

class PublicationListFragment : BaseMvpFragment(), PublicationListView {
    @InjectPresenter
    lateinit var presenter: PublicationListPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_publication_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshLayout.setOnRefreshListener { presenter.onRefresh() }
        rvPublications.layoutManager = LinearLayoutManager(context)
    }

    override fun showPublications(publications: List<Publication>) {
        val groupAdapter = GroupAdapter<ViewHolder>()
        groupAdapter.addAll(publications.map { PublicationItem(it) })
        rvPublications.adapter = groupAdapter

        rvPublications.paging({ presenter.loadMore() }, emptyListCount = publications.size)
    }

    override fun addPublications(publications: List<Publication>) {
        (rvPublications.adapter as? GroupAdapter)?.addAll(publications.map { PublicationItem(it) })
    }

    override fun clearPublications() {
        (rvPublications.adapter as? GroupAdapter)?.clear()
    }

    override fun setupToolbar(appBar: AppBarLayout): Toolbar? {
        return provideSimpleToolbar(getString(R.string.publications), appBar).also {
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
        fun newInstance(): PublicationListFragment {
            return PublicationListFragment()
        }
    }
}
