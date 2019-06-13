package etu.uportal.model.data

import java.io.Serializable


data class AuthorDetailed(
        val fields: List<AuthorField>,
        val publications: List<Publication>,
        val firstName: String,
        val firstNameEn: String,
        val id: Int,
        val lastName: String,
        val lastNameEn: String,
        val middleName: String,
        val middleNameEn: String,
        val publicationQty: Int
) : Serializable {
    val fullName: String
        get() {
            return "$firstName $middleName $lastName"
        }

    val fullNameEng: String
        get() {
            return "$firstNameEn $middleNameEn $lastNameEn"
        }

    val extraFieldsStr: String?
        get() {
            val stringFields = fields.map { it.name + ": " + it.value }
            return if (stringFields.isEmpty()) {
                null
            } else {
                stringFields.reduce { resStr, s -> resStr + "\n" + s }
            }
        }
}