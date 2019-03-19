package etu.uportal.di.module

import dagger.Module
import dagger.Provides
import etu.uportal.model.network.content.ContentApi
import etu.uportal.model.network.user.UserApi
import retrofit2.Retrofit
import javax.inject.Named

@Module(includes = [RetrofitModule::class])
class ApiModule {
    @Provides
    fun provideUserApi(@Named("auth-api") retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    fun provideContentApi(@Named("content-api") retrofit: Retrofit): ContentApi {
        return retrofit.create(ContentApi::class.java)
    }
}