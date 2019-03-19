package etu.uportal.ui.adapter.items
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import etu.uportal.R
import etu.uportal.model.data.User
import kotlinx.android.synthetic.main.card_user.*

open class UserItem(val user: User): Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
      viewHolder.tvUserName.text = user.email
    }

    override fun getLayout(): Int = R.layout.card_user
}