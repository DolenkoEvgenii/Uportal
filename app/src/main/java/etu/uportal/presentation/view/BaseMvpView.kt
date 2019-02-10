package etu.uportal.presentation.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface BaseMvpView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showLoadingDialog()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun closeLoadingDialog()
}
