package etu.uportal.model.data

data class Author(
        val createdAt: Int,
        val firstName: String,
        val firstNameEn: String,
        val id: Int,
        val lastName: String,
        val lastNameEn: String,
        val middleName: String,
        val middleNameEn: String,
        val updatedAt: Int
) {
    val fullName: String
        get() {
            return "$firstName $middleName $lastName"
        }
}