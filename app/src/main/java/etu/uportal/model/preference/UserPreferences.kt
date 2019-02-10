package etu.uportal.model.preference

import android.content.Context
import android.preference.PreferenceManager
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.google.gson.Gson
import io.reactivex.Observable
import etu.uportal.model.data.User
import javax.inject.Inject

class UserPreferences @Inject constructor(context: Context) {
    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val rxPreferences = RxSharedPreferences.create(preferences)

    val isAuthorized: Boolean
        get() = rxPreferences.getString(USER_ARG).get().isNotBlank()

    val authToken: String
        get() = rxPreferences.getString(TOKEN_ARG).get()

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

    fun saveUser(user: User) {
        saveToken(user)
        val userJson = Gson().toJson(user)
        preferences.edit().putString(USER_ARG, userJson).apply()
    }

    fun saveToken(user: User) {
        //preferences.edit().putString(TOKEN_ARG, user.token).apply()
    }


    private fun clearUserData() {
        rxPreferences.clear()
    }

    companion object {
        const val USER_ARG = "user_arg"
        const val TOKEN_ARG = "token_arg"
    }
}