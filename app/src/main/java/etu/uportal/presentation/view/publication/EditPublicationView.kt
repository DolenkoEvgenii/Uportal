package etu.uportal.presentation.view.publication

import etu.uportal.model.data.PublicationField
import etu.uportal.presentation.view.BaseMvpView

interface EditPublicationView : BaseMvpView {
    fun showPickedAuthors(authors: List<String>)

    fun bindData(title: String, intro: String, fields: List<PublicationField>)
}
