package etu.uportal.model.network.data.request

data class CreatePublicationRequest(
        val authorListId: List<Int>,
        val title: String,
        val introText: String,
        val publicationFields: List<ExtraFields>,
        val publishedAt: Long = System.currentTimeMillis() / 1000
) {
    data class ExtraFields(val name: String, val value: String)
}