package etu.uportal.presentation.view.user

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import etu.uportal.model.data.User
import etu.uportal.presentation.view.BaseMvpView

interface UserListView : BaseMvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showUsers(users: List<User>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun addUsers(users: List<User>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun clearUsers()
}
