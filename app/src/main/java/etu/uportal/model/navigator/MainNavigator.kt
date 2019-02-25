package etu.uportal.model.navigator

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import etu.uportal.R
import etu.uportal.ui.activity.base.BaseMvpFragmentActivity
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace

class MainNavigator(activity: BaseMvpFragmentActivity,
                    containerId: Int = R.id.vFragmentContainer) : SupportAppNavigator(activity, containerId) {

    override fun setupFragmentTransaction(command: Command, currentFragment: Fragment?, nextFragment: Fragment, fragmentTransaction: FragmentTransaction) {
        when {
            command is Forward -> {
                fragmentTransaction.setCustomAnimations(
                        R.anim.push_left_in_no_alpha,
                        R.anim.push_left_out_no_alpha,
                        R.anim.push_right_in_no_alpha,
                        R.anim.push_right_out_no_alpha)
            }

            command is Replace -> {
                fragmentTransaction.setCustomAnimations(
                        R.anim.fade_in,
                        R.anim.fade_out)
            }

            else -> super.setupFragmentTransaction(command, currentFragment, nextFragment, fragmentTransaction)
        }
    }
}