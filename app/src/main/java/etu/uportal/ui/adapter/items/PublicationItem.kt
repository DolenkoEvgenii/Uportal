package etu.uportal.ui.adapter.items

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import etu.uportal.App
import etu.uportal.R
import etu.uportal.Screens
import etu.uportal.model.data.Publication
import etu.uportal.utils.helpers.click
import kotlinx.android.synthetic.main.card_publication.*
import kotlinx.android.synthetic.main.card_publication.view.*

open class PublicationItem(val publication: Publication) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvPublicationName.text = publication.title
        viewHolder.tvPublicationIntro.text = publication.introText
        viewHolder.tvPublicationAuthors.text = publication.authors

        viewHolder.itemView.vPublicationCard.click {
            App.component.router().navigateTo(Screens.PublicationDetailedFragmentScreen(publication).apply { inNewActivity = true })
        }
    }

    override fun getLayout(): Int = R.layout.card_publication
}