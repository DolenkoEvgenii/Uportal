package etu.uportal.model.models

import etu.uportal.model.data.Author

object AuthorsModel {
    val authorList: MutableList<Author> = ArrayList()

    val offset: Int
        get() = authorList.size

    val isEmpty: Boolean
        get() = authorList.isEmpty()

    fun clear() {
        authorList.clear()
    }

    fun addAll(authors: List<Author>) {
        authorList.addAll(authors)
    }
}