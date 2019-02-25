package etu.uportal.model.preference

import android.content.Context
import android.preference.PreferenceManager
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.google.gson.Gson
import etu.uportal.model.data.User
import etu.uportal.model.network.data.response.AuthResponse
import io.reactivex.Observable
import javax.inject.Inject

class UserPreferences @Inject constructor(context: Context) {
    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val rxPreferences = RxSharedPreferences.create(preferences)

    val isAuthorized: Boolean
        get() = authToken.isNotBlank()

    val authToken: String
        get() = rxPreferences.getString(TOKEN_ARG).get()

    val refreshToken: String
        get() = rxPreferences.getString(REFRESH_TOKEN_ARG).get()

    fun getUserLocal(): Observable<User> {
        return rxPreferences.getString(USER_ARG)
                .asObservable()
                .map { userJson ->
                    if (userJson.isEmpty()) throw Exception("no saved user")
                    return@map Gson().fromJson(userJson, User::class.java)
                }
                .take(1)
    }

    fun getUserLocalBlocking(): User {
        return getUserLocal().blockingFirst()
    }

    /* fun saveUser(user: User) {
         saveTokens(user)
         val userJson = Gson().toJson(user)
         preferences.edit().putString(USER_ARG, userJson).apply()
     }*/

    fun saveTokens(authData: AuthResponse) {
        preferences.edit().putString(TOKEN_ARG, authData.accessToken).apply()
        preferences.edit().putString(REFRESH_TOKEN_ARG, authData.refreshToken).apply()
    }

    fun clearUserData() {
        rxPreferences.clear()
    }

    companion object {
        const val USER_ARG = "user_arg"
        const val TOKEN_ARG = "token_arg"
        const val REFRESH_TOKEN_ARG = "refresh_token_arg"
    }
}