package etu.uportal.model.network.content

import android.content.Context
import etu.uportal.model.data.Author
import etu.uportal.model.data.AuthorDetailed
import etu.uportal.model.data.Publication
import etu.uportal.model.data.User
import etu.uportal.model.network.BaseRepository
import etu.uportal.model.network.data.response.pagination.PaginationResponse
import etu.uportal.utils.pagination.PaginationTool
import io.reactivex.Observable
import javax.inject.Inject


class ContentRepository @Inject constructor(private val contentApi: ContentApi, context: Context) : BaseRepository(context) {

    fun getUsers(offset: Int, limit: Int = PaginationTool.DEFAULT_PAGE_SIZE): Observable<PaginationResponse<User>> {
        return contentApi.getUsers(offset, limit)
                .compose(handleErrors())
    }

    fun getAuthors(offset: Int, limit: Int = PaginationTool.DEFAULT_PAGE_SIZE): Observable<PaginationResponse<Author>> {
        return contentApi.getAuthors(offset, limit)
                .compose(handleErrors())
    }

    fun getAuthorDetailed(authorId: Int): Observable<AuthorDetailed> {
        return contentApi.getAuthor(authorId).compose(handleErrors())
    }

    fun getPublications(offset: Int, search: String? = null, limit: Int = PaginationTool.DEFAULT_PAGE_SIZE): Observable<PaginationResponse<Publication>> {
        if (search.isNullOrBlank()) {
            return contentApi.getPublications(offset, limit).compose(handleErrors())
        } else {
            return contentApi.searchPublications(search, offset, limit).compose(handleErrors())
        }
    }
}
