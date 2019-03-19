package etu.uportal.presentation.view.menu

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import etu.uportal.presentation.view.BaseMvpView

@StateStrategyType(AddToEndSingleStrategy::class)
interface MenuView : BaseMvpView {
    fun selectDrawerItemWithId(id: Long)
}
