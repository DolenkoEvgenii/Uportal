package etu.uportal.model.network.content


import etu.uportal.model.data.Author
import etu.uportal.model.data.AuthorDetailed
import etu.uportal.model.data.Publication
import etu.uportal.model.data.User
import etu.uportal.model.network.data.request.*
import etu.uportal.model.network.data.response.pagination.PaginationResponse
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ContentApi {
    @Headers("accept:application/json")
    @GET("api/user/")
    fun getUsers(@Query("offset") offset: Int, @Query("limit") limit: Int): Observable<Response<PaginationResponse<User>>>

    @Headers("accept:application/json")
    @POST("api/user")
    fun createUser(@Body request: RefreshRequest): Observable<Response<Any>>

    @Headers("accept:application/json")
    @GET("api/author/")
    fun getAuthors(@Query("offset") offset: Int, @Query("limit") limit: Int): Observable<Response<PaginationResponse<Author>>>

    @Headers("accept:application/json")
    @GET("api/author/{id}")
    fun getAuthor(@Path("id") authorId: Int): Observable<Response<AuthorDetailed>>

    @Headers("accept:application/json")
    @DELETE("api/author/{id}")
    fun deleteAuthor(@Path("id") authorId: Int): Observable<Response<ResponseBody>>


    @Headers("accept:application/json")
    @GET("api/publication/")
    fun getPublications(@Query("offset") offset: Int, @Query("limit") limit: Int): Observable<Response<PaginationResponse<Publication>>>

    @Headers("accept:application/json")
    @DELETE("api/publication/{id}")
    fun deletePublication(@Path("id") publicationId: Int): Observable<Response<ResponseBody>>

    @Headers("accept:application/json")
    @GET("api/publication/search")
    fun searchPublications(@Query("query") query: String, @Query("offset") offset: Int, @Query("limit") limit: Int): Observable<Response<PaginationResponse<Publication>>>

    @Headers("accept:application/json")
    @POST("api/publication/")
    fun createPublication(@Body request: CreatePublicationRequest): Observable<Response<ResponseBody>>

    @Headers("accept:application/json")
    @POST("api/publication/{id}")
    fun editPublication(@Path("id") publicationId: Int, @Body request: EditPublicationRequest): Observable<Response<ResponseBody>>


    @Headers("accept:application/json")
    @POST("api/author/")
    fun createAuthor(@Body request: CreateAuthorRequest): Observable<Response<ResponseBody>>

    @Headers("accept:application/json")
    @POST("api/author/{id}")
    fun editAuthor(@Path("id") authorId: Int, @Body request: EditAuthorRequest): Observable<Response<ResponseBody>>
}