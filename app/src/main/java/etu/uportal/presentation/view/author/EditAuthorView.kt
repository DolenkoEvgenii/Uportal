package etu.uportal.presentation.view.author

import etu.uportal.model.data.Author
import etu.uportal.presentation.view.BaseMvpView

interface EditAuthorView : BaseMvpView {
    fun bindData(author: Author)
}
