package etu.uportal.presentation.view.author

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import etu.uportal.model.data.Author
import etu.uportal.presentation.view.BaseMvpView

interface SelectAuthorView : BaseMvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showAuthors(users: List<Author>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun addAuthors(users: List<Author>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun clearAuthors()
}
