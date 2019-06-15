package etu.uportal.ui.adapter.items

import android.annotation.SuppressLint
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import etu.uportal.R
import etu.uportal.model.data.Author
import kotlinx.android.synthetic.main.card_author.*
import kotlinx.android.synthetic.main.card_select_author.view.*

open class SelectAuthorItem(val author: Author, private val selectAuthorDelegate: SelectAuthorDelegate) : Item() {
    @SuppressLint("SetTextI18n")
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvAuthorName.text = author.fullName

        viewHolder.itemView.setOnClickListener {
            selectAuthorDelegate.onAuthorClicked(author)
            updateCheckedState(viewHolder)
        }

        viewHolder.itemView.cbAuthor.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (compoundButton.isPressed) {
                selectAuthorDelegate.onAuthorClicked(author)
                updateCheckedState(viewHolder)
            }
        }
    }

    private fun updateCheckedState(viewHolder: ViewHolder) {
        val isChecked = selectAuthorDelegate.isAuthorSelected(author)
        viewHolder.itemView.cbAuthor.isChecked = isChecked
    }

    override fun getLayout(): Int = R.layout.card_select_author

    interface SelectAuthorDelegate {
        fun onAuthorClicked(author: Author)

        fun isAuthorSelected(author: Author): Boolean
    }
}