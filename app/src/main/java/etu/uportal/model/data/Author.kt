package etu.uportal.model.data

import java.io.Serializable


data class Author(
        val allowDelete: Boolean,
        val fields: List<AuthorField>,
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
}