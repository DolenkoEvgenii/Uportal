package etu.uportal.ui.adapter.items

import android.annotation.SuppressLint
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import etu.uportal.App
import etu.uportal.R
import etu.uportal.Screens
import etu.uportal.model.data.Author
import etu.uportal.utils.helpers.click
import etu.uportal.utils.helpers.getString
import kotlinx.android.synthetic.main.card_author.*
import kotlinx.android.synthetic.main.card_author.view.*

open class AuthorItem(val author: Author) : Item() {

    @SuppressLint("SetTextI18n")
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvAuthorName.text = author.fullName
        viewHolder.tvAuthorPublicationsCount.text = getString(R.string.publications_count) + ": " + author.publicationQty

        viewHolder.itemView.vAuthorCard.click {
            App.component.router().navigateTo(Screens.AuthorDetailedFragmentScreen(author).apply { inNewActivity = true })
        }
    }

    override fun getLayout(): Int = R.layout.card_author
}