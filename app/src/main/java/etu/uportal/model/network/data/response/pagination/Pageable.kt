package etu.uportal.model.network.data.response.pagination

data class Pageable(
        val limit: Int,
        val offset: Int,
        val pageNumber: Int,
        val pageSize: Int,
        val paged: Boolean,
        val sort: Sort,
        val unpaged: Boolean
)