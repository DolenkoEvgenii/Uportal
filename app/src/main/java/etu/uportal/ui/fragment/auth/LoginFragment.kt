package etu.uportal.ui.fragment.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.appbar.AppBarLayout
import etu.uportal.R
import etu.uportal.presentation.presenter.auth.LoginPresenter
import etu.uportal.presentation.view.auth.LoginView
import etu.uportal.ui.fragment.BaseMvpFragment
import etu.uportal.utils.helpers.click
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseMvpFragment(), LoginView {
    @InjectPresenter
    lateinit var presenter: LoginPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btLogin.click { presenter.onLoginBtnClicked(etLogin.text.toString(), etPassword.text.toString()) }
    }

    override fun setupToolbar(appBar: AppBarLayout): Toolbar? {
        return null
    }

    companion object {
        const val TAG = "LoginFragment"
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }
}
