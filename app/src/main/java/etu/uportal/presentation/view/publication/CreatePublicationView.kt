package etu.uportal.presentation.view.publication

import etu.uportal.presentation.view.BaseMvpView

interface CreatePublicationView : BaseMvpView {
    fun showPickedAuthors(authors: List<String>)
}
