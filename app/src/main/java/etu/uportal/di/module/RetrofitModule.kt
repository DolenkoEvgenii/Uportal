package etu.uportal.di.module

import android.content.Context
import android.os.Build
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import etu.uportal.AppConstants
import etu.uportal.BuildConfig
import etu.uportal.model.preference.UserPreferences
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class RetrofitModule {
    @Provides
    @Singleton
    @Named("auth-api")
    fun provideRetrofit(builder: Retrofit.Builder): Retrofit {
        return builder.baseUrl(AppConstants.API_URL).build()
    }

    @Provides
    @Singleton
    @Named("content-api")
    fun provideRetrofitApi(builder: Retrofit.Builder): Retrofit {
        return builder.baseUrl(AppConstants.API_URL).build()
    }


    @Provides
    @Singleton
    fun provideRetrofitBuilder(converterFactory: Converter.Factory, okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(converterFactory)
    }

    @Provides
    @Singleton
    fun provideConverterFactory(gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setPrettyPrinting()
                .registerTypeAdapter(Date::class.java, MyDateTypeAdapter())
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .serializeNulls()
                .create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(userPreferences: UserPreferences, context: Context): OkHttpClient {
        return OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(AuthInterceptor(userPreferences))
                .addInterceptor(UserAgentInterceptor(createUserAgent(context)))
                .addInterceptor(ChuckInterceptor(context))
                .build()
    }

    private fun createUserAgent(context: Context): String {
        val versionCode = BuildConfig.VERSION_CODE.toString()
        val androidOCVersion = Build.VERSION.RELEASE
        val model = Build.MODEL

        val displayMetrics = context.resources.displayMetrics
        val screenResolution = String.format(Locale.getDefault(), "%dx%d",
                displayMetrics.heightPixels, displayMetrics.widthPixels)

        return String.format("Android_%s_v_%s_%s_%s", androidOCVersion, versionCode, model, screenResolution)
    }

    class UserAgentInterceptor constructor(private val userAgent: String) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val requestWithUserAgent = originalRequest.newBuilder()
                    .header("User-Agent", userAgent)
                    .build()
            return chain.proceed(requestWithUserAgent)
        }
    }

    class AuthInterceptor constructor(private val userPreferences: UserPreferences) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()

            return if (original.headers().names().contains("Authorization")) {
                chain.proceed(original)
            } else {
                var requestBuilder = original.newBuilder()
                if (userPreferences.authToken.isNotBlank()) {
                    val token = "Bearer ${userPreferences.authToken}"
                    requestBuilder = requestBuilder.header("Authorization", token)
                }

                val request = requestBuilder.method(original.method(), original.body())
                        .build()

                chain.proceed(request)
            }
        }
    }

    class MyDateTypeAdapter : TypeAdapter<Date>() {
        override fun write(out: JsonWriter, value: Date?) {
            if (value == null) {
                out.nullValue()
            } else {
                out.value(value.time / 1000)
            }
        }

        override fun read(reader: JsonReader?): Date? {
            return if (reader != null) {
                Date(reader.nextLong() * 1000)
            } else {
                null
            }
        }
    }
}