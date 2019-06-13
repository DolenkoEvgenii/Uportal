package etu.uportal.model.models

import etu.uportal.model.data.Publication

object PublicationsModel {
    var searchQuery = ""
    val publicationList: MutableList<Publication> = ArrayList()

    val offset: Int
        get() = publicationList.size

    val isEmpty: Boolean
        get() = publicationList.isEmpty()

    fun clear() {
        publicationList.clear()
    }

    fun addAll(publications: List<Publication>) {
        publicationList.addAll(publications)
    }
}