package etu.uportal.model.network.user


import etu.uportal.model.network.data.request.LoginRequest
import etu.uportal.model.network.data.request.RefreshRequest
import etu.uportal.model.network.data.response.AuthResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserApi {
    @Headers("Content-Type:application/json")
    @POST("oauth/token")
    fun login(@Body request: LoginRequest): Observable<Response<AuthResponse>>

    @Headers("Content-Type:application/json")
    @POST("oauth/token")
    fun refreshToken(@Body request: RefreshRequest): Observable<Response<AuthResponse>>
}