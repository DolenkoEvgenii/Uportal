package etu.uportal.model.network

import android.annotation.SuppressLint
import android.content.Context
import etu.uportal.App
import etu.uportal.R
import etu.uportal.Screens
import etu.uportal.model.exception.APIException
import etu.uportal.model.network.data.request.RefreshRequest
import etu.uportal.model.network.data.response.AuthResponse
import etu.uportal.model.network.user.UserApi
import etu.uportal.model.preference.UserPreferences
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import retrofit2.Response
import ru.terrakok.cicerone.Router
import java.net.HttpURLConnection
import java.net.UnknownHostException
import javax.inject.Inject


open class BaseRepository(val context: Context) {
    private val utilityWrapper = ApiUtilityWrapper()

    init {
        App.component.inject(utilityWrapper)
    }

    fun refreshToken(): Observable<AuthResponse> {
        val refreshToken = utilityWrapper.userPreferences.refreshToken

        return utilityWrapper.userApi
                .refreshToken(RefreshRequest(refreshToken))
                .compose(handleErrors(false))
                .doOnNext { utilityWrapper.userPreferences.saveTokens(it) }
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
    @SuppressLint("CheckResult")
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

    private fun convertExceptionToText(exc: Throwable): String {
        return when (exc) {
            is UnknownHostException -> context.getString(R.string.no_internet_connection)
            is InterruptedException -> context.getString(R.string.server_error)
            is APIException -> exc.localizedMessage
            else -> context.getString(R.string.server_timeout_error)
        }
    }

    private fun <T> getErrorInstance(response: Response<T>): APIException {
        val httpCode = response.code()
        val body = response.errorBody()?.string()

        val message = when (httpCode) {
            HttpURLConnection.HTTP_UNAUTHORIZED -> "Сессия истекла"
            else -> "Неизвестная ошибка $httpCode"
        }

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