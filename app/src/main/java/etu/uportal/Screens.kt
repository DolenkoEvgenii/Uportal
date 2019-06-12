package etu.uportal

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import etu.uportal.model.data.Publication
import etu.uportal.model.navigator.BaseAppScreen
import etu.uportal.ui.activity.auth.AuthActivity
import etu.uportal.ui.activity.menu.MenuActivity
import etu.uportal.ui.fragment.auth.LoginFragment
import etu.uportal.ui.fragment.author.AuthorListFragment
import etu.uportal.ui.fragment.publication.PublicationDetailedFragment
import etu.uportal.ui.fragment.publication.PublicationListFragment
import etu.uportal.ui.fragment.user.UserListFragment

object Screens {
    class MenuActivityScreen : BaseAppScreen() {
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, MenuActivity::class.java)
        }
    }

    class AuthActivityScreen : BaseAppScreen() {
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, AuthActivity::class.java)
        }
    }

    class LoginFragmentScreen : BaseAppScreen() {
        override fun getFragment(): Fragment {
            return LoginFragment.newInstance()
        }
    }


    class UserListFragmentScreen : BaseAppScreen() {
        override fun getFragment(): Fragment {
            return UserListFragment.newInstance()
        }
    }

    class AuthorListFragmentScreen : BaseAppScreen() {
        override fun getFragment(): Fragment {
            return AuthorListFragment.newInstance()
        }
    }

    class PublicationListFragmentScreen : BaseAppScreen() {
        override fun getFragment(): Fragment {
            return PublicationListFragment.newInstance()
        }
    }

    class PublicationDetailedFragmentScreen(val publication: Publication) : BaseAppScreen() {
        override fun getFragment(): Fragment {
            return PublicationDetailedFragment.newInstance(publication)
        }
    }
}
