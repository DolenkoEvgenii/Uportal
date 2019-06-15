package etu.uportal.model.network.data.request

data class EditPublicationRequest(
        val authorListId: List<Int>,
        val title: String,
        val introText: String,
        val publicationFields: List<ExtraFields>,
        val publishedAt: Long
) {
    data class ExtraFields(val name: String, val value: String)
}