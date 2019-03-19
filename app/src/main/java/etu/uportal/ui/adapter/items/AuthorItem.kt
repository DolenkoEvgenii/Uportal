package etu.uportal.ui.adapter.items

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import etu.uportal.R
import etu.uportal.model.data.Author
import kotlinx.android.synthetic.main.card_author.*

open class AuthorItem(val author: Author) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvAuthorName.text = author.fullName
    }

    override fun getLayout(): Int = R.layout.card_author
}