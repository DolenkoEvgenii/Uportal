package etu.uportal.model.network.data.request

data class EditAuthorRequest(
        val firstName: String,
        val firstNameEn: String,
        val lastName: String,
        val lastNameEn: String,
        val middleName: String,
        val middleNameEn: String,
        val authorFields: List<ExtraFields>
) {
    data class ExtraFields(val name: String, val value: String)
}