package etu.uportal.model.network.content


import etu.uportal.model.data.User
import etu.uportal.model.network.data.request.RefreshRequest
import etu.uportal.model.network.data.response.pagination.PaginationResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface ContentApi {
    @Headers("accept:application/json")
    @GET("api/user/")
    fun getUsers(@Query("offset") offset: Int, @Query("limit") limit: Int): Observable<Response<PaginationResponse<User>>>

    @Headers("accept:application/json")
    @POST("api/user")
    fun createUser(@Body request: RefreshRequest): Observable<Response<Any>>
}