package etu.uportal.model.network.user

import android.content.Context
import etu.uportal.model.network.BaseRepository
import etu.uportal.model.network.data.request.LoginRequest
import etu.uportal.model.network.data.response.AuthResponse
import etu.uportal.model.preference.UserPreferences
import io.reactivex.Observable
import javax.inject.Inject


class UserRepository @Inject constructor(private val userApi: UserApi,
                                         private val userPreferences: UserPreferences,
                                         context: Context) : BaseRepository(context) {

    val isAuthorized: Boolean
        get() = userPreferences.isAuthorized

    fun login(login: String, password: String): Observable<AuthResponse> {
        return userApi.login(LoginRequest(login, password))
                .compose(handleErrors())
                .doOnNext { userPreferences.saveTokens(it) }
    }
}
