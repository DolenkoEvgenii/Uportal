package etu.uportal.model.network


import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import etu.uportal.model.data.TestClass


interface TestApi {
    @GET("/api/v1/caches")
    operator fun get(@Query("md5") md5: String): Observable<Response<TestClass>>

    @DELETE("/api/v1/sessions")
    fun delete(): Observable<ResponseBody>

    @Headers("content-type: application/json")
    @POST("/api/v1/rides")
    fun post(@Body ride: TestClass): Observable<Response<TestClass>>

    @Headers("content-type: application/json")
    @PUT("/api/v1/rides/{id}")
    fun put(@Path("id") rideId: Int, @Body confirmRideRequest: TestClass): Observable<Response<TestClass>>
}