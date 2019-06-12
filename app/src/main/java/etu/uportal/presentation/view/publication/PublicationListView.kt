package etu.uportal.presentation.view.publication

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import etu.uportal.model.data.Publication
import etu.uportal.presentation.view.BaseMvpView

interface PublicationListView : BaseMvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showPublications(publications: List<Publication>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun addPublications(publications: List<Publication>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun clearPublications()
}
