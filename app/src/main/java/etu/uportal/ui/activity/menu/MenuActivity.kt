package etu.uportal.ui.activity.menu

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import co.zsmb.materialdrawerkt.builders.drawer
import co.zsmb.materialdrawerkt.draweritems.badgeable.primaryItem
import co.zsmb.materialdrawerkt.draweritems.divider
import com.arellomobile.mvp.presenter.InjectPresenter
import com.mikepenz.materialdrawer.Drawer
import etu.uportal.R
import etu.uportal.model.event.ToolbarUpdatedEvent
import etu.uportal.presentation.presenter.menu.MenuPresenter
import etu.uportal.presentation.view.menu.MenuView
import etu.uportal.ui.activity.base.BaseMvpFragmentActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MenuActivity : BaseMvpFragmentActivity(), MenuView, FragmentManager.OnBackStackChangedListener {
    @InjectPresenter
    lateinit var presenter: MenuPresenter

    private lateinit var drawer: Drawer
    private lateinit var drawerArrowDrawable: DrawerArrowDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        initDrawer()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun selectDrawerItemWithId(id: Long) {
        drawer.setSelection(id, false)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onToolbarUpdatedEvent(event: ToolbarUpdatedEvent) {
        findViewById<View>(R.id.toolbar)?.let {
            drawer.setToolbar(this, it as Toolbar, true)
            drawer.actionBarDrawerToggle.drawerArrowDrawable = drawerArrowDrawable
        }
    }

    private fun initDrawer() {
        drawer = drawer {
            headerViewRes = R.layout.drawer_header
            headerHeightDp = 120
            headerDivider = false

            primaryItem(R.string.users) {
                identifier = TAB_USERS
                iconTintingEnabled = true
                icon = R.drawable.ic_user
                iconColorRes = R.color.colorDrawerItemDisabled
                selectedIconColorRes = R.color.primary
            }

            primaryItem(R.string.authors) {
                identifier = TAB_AUTHORS
                iconTintingEnabled = true
                icon = R.drawable.ic_author
                iconColorRes = R.color.colorDrawerItemDisabled
                selectedIconColorRes = R.color.primary
            }

            divider {}

            primaryItem(R.string.logout) {
                identifier = TAB_LOGOUT
                iconTintingEnabled = true
                icon = R.drawable.ic_logout
                iconColorRes = R.color.colorDrawerItemDisabled
                selectedIconColorRes = R.color.primary
            }

            onItemClick { _, _, drawerItem ->
                presenter.onDrawerItemClick(drawerItem.identifier)
                return@onItemClick false
            }
        }


        drawerArrowDrawable = DrawerArrowDrawable(this)
        drawerArrowDrawable.color = Color.WHITE
    }

    override fun onBackStackChanged() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            drawer.drawerLayout.setDrawerLockMode(androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        } else {
            drawer.drawerLayout.setDrawerLockMode(androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED)
        }
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    companion object {
        const val TAB_USERS = 1L
        const val TAB_AUTHORS = 2L
        const val TAB_LOGOUT = 3L
    }
}
