package etu.uportal.ui.activity.base

import android.os.Bundle
import android.os.Handler
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.appbar.AppBarLayout
import etu.uportal.App
import etu.uportal.R
import etu.uportal.model.navigator.MainNavigator
import etu.uportal.utils.provider.LoadingDialogProvider
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

abstract class BaseMvpFragmentActivity : BaseRxFragmentActivity() {
    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private lateinit var navigator: Navigator
    private var sweetAlertDialog: SweetAlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
        navigator = MainNavigator(this)
    }

    private fun initDialog() {
        sweetAlertDialog = LoadingDialogProvider.getProgressDialog(this)
    }

    fun showLoadingDialog() {
        if (sweetAlertDialog == null) {
            initDialog()
        }

        if (sweetAlertDialog?.isShowing != true) {
            sweetAlertDialog?.show()
        }
    }

    fun closeLoadingDialog() {
        sweetAlertDialog?.let { dialog ->
            if (dialog.isShowing) {
                dialog.dismissWithAnimation()
                Handler().postDelayed({
                    if (dialog.isShowing) {
                        dialog.dismiss()
                        sweetAlertDialog = null
                    }
                }, 100)
            }
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    open fun provideAppBar(): AppBarLayout? {
        return findViewById(R.id.appBar)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}
