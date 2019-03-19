package etu.uportal.ui.fragment.user

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
import etu.uportal.model.data.User
import etu.uportal.presentation.presenter.user.UserListPresenter
import etu.uportal.presentation.view.user.UserListView
import etu.uportal.ui.adapter.items.UserItem
import etu.uportal.ui.fragment.BaseMvpFragment
import etu.uportal.utils.pagination.paging
import kotlinx.android.synthetic.main.fragment_user_list.*


class UserListFragment : BaseMvpFragment(), UserListView {
    @InjectPresenter
    lateinit var presenter: UserListPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshLayout.setOnRefreshListener { presenter.onRefresh() }
        rvUsers.layoutManager = LinearLayoutManager(context)
    }

    override fun showUsers(users: List<User>) {
        val groupAdapter = GroupAdapter<ViewHolder>()
        groupAdapter.addAll(users.map { UserItem(it) })
        rvUsers.adapter = groupAdapter

        rvUsers.paging({ presenter.loadMore() }, emptyListCount = users.size)
    }

    override fun addUsers(users: List<User>) {
        (rvUsers.adapter as? GroupAdapter)?.addAll(users.map { UserItem(it) })
    }

    override fun clearUsers() {
        (rvUsers.adapter as? GroupAdapter)?.clear()
    }

    override fun setupToolbar(appBar: AppBarLayout): Toolbar? {
        return provideSimpleToolbar(getString(R.string.users), appBar).also {
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
        fun newInstance(): UserListFragment {
            return UserListFragment()
        }
    }
}
