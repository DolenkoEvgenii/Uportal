package etu.uportal.model.network.data.response.pagination

data class PaginationResponse<T>(
        val content: List<T>,
        val first: Boolean,
        val last: Boolean,
        val number: Int,
        val numberOfElements: Int,
        val pageable: Any?,
        val size: Int,
        val sort: Sort,
        val totalElements: Int,
        val totalPages: Int
)