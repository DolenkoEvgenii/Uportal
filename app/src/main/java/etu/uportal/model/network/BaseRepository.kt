package etu.uportal.model.network

import android.content.Context
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Function
import org.json.JSONObject
import retrofit2.Response
import ru.terrakok.cicerone.Router
import etu.uportal.App
import etu.uportal.R
import etu.uportal.model.exception.APIException
import etu.uportal.model.network.user.UserApi
import etu.uportal.model.preference.UserPreferences
import java.net.UnknownHostException
import javax.inject.Inject


open class BaseRepository(val context: Context) {
    private val utilityWrapper = ApiUtilityWrapper()

    init {
        App.component.inject(utilityWrapper)
    }

    private fun convertExceptionToText(exc: Throwable): String {
        return when (exc) {
            is UnknownHostException -> context.getString(R.string.no_internet_connection)
            is InterruptedException -> context.getString(R.string.server_error)
            is APIException -> exc.localizedMessage
            else -> context.getString(R.string.server_timeout_error)
        }
    }

    fun <T> handleErrors(): ObservableTransformer<Response<T>, T> {
        return ObservableTransformer { observable ->
            observable
                    .onErrorResumeNext(Function { error ->
                        error.printStackTrace()
                        return@Function Observable.error(APIException(convertExceptionToText(error)))
                    })
                    .map { response ->
                        if (response.isSuccessful) {
                            return@map response.body()
                        } else {
                            throw getErrorInstance(response)
                        }
                    }
        }
    }

    // OAuth2

    /*@SuppressLint("CheckResult")
    fun <T> handleErrors(needRetry: Boolean = true): ObservableTransformer<Response<T>, T> {
        return ObservableTransformer { observable ->
            observable
                    .onErrorResumeNext(Function { error ->
                        error.printStackTrace()
                        return@Function Observable.error(APIException(convertExceptionToText(error)))
                    })
                    .map { response ->
                        if (response.isSuccessful) {
                            return@map response.body()
                        } else {
                            throw getErrorInstance(response)
                        }
                    }
                    .retry { times, exc ->
                        if (!needRetry) {
                            return@retry false
                        }

                        if (exc is APIException && exc.httpCode == 401) {
                            if (times == 1) {
                                refreshToken().map { 1 }.onErrorResumeNext(Observable.just(1)).blockingFirst()
                                return@retry true
                            } else {
                                Completable.complete()
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe {
                                            utilityWrapper.userPreferences.clearUserData()
                                            utilityWrapper.router.newRootScreen(Screens.AuthActivityScreen())
                                        }
                                return@retry false
                            }
                        } else {
                            return@retry false
                        }
                    }
        }
    }

    fun refreshToken(): Observable<User> {
        val username = utilityWrapper.userPreferences.getUserLocal().blockingFirst().username
        val refreshToken = utilityWrapper.userPreferences.refreshToken
        val request = utilityWrapper.userApi.refresh(RefreshRequest(username, refreshToken))

        return request
                .compose(handleErrors(false))
                .doOnNext {
                    utilityWrapper.userPreferences.saveUser(it)
                }
    }*/

    private fun <T> getErrorInstance(response: Response<T>): APIException {
        val httpCode = response.code()
        val body = response.errorBody()?.string()

        val message = body?.let {
            try {
                val jsonError = JSONObject(it)
                return@let jsonError.optString("message") ?: ""
            } catch (exc: Exception) {
                return@let body
            }
        } ?: ""

        return APIException(message, httpCode, body)
    }

    class ApiUtilityWrapper {
        @Inject
        lateinit var userApi: UserApi

        @Inject
        lateinit var userPreferences: UserPreferences

        @Inject
        lateinit var router: Router
    }
}