package etu.uportal.model.network.user

import android.content.Context
import io.reactivex.Observable
import etu.uportal.model.data.User
import etu.uportal.model.network.BaseRepository
import etu.uportal.model.preference.UserPreferences
import javax.inject.Inject


class UserRepository @Inject constructor(private val userApi: UserApi,
                                         private val userPreferences: UserPreferences,
                                         context: Context) : BaseRepository(context) {

    val isAuthorized: Boolean
        get() = userPreferences.isAuthorized

    fun getUserLocal(): Observable<User> {
        return userPreferences.getUserLocal()
    }
}
