package etu.uportal.model.data

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*


data class Publication(
        val authorList: List<Author>,
        val authorListId: List<Any>,
        val id: Int,
        val introText: String,
        val publicationFields: List<PublicationField>,
        val publishedAt: Date,
        val title: String
) : Serializable {

    val publishedDate: String
        get() {
            return SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.getDefault()).format(publishedAt)
        }

    val authors: String
        get() {
            if (authorList.isEmpty()) {
                return "Автор неизвестен"
            } else {
                return authorList.map { it.fullName }.reduce { resStr, authorName -> "$resStr, $authorName" }
            }
        }


    val extraFieldsStr: String?
        get() {
            val stringFields = publicationFields.map { it.name + ": " + it.value }
            return if (stringFields.isEmpty()) {
                null
            } else {
                stringFields.reduce { resStr, s -> resStr + "\n" + s }
            }
        }
}