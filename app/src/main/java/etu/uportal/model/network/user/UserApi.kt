package etu.uportal.model.network.user


import io.reactivex.Observable
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
    @POST("auth/login")
    fun loginByEmail(@Body request: Request): Observable<Response<ResponseBody>>
}