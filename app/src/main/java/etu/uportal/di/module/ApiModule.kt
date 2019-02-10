package etu.uportal.di.module

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import etu.uportal.model.network.user.UserApi
import javax.inject.Named

@Module(includes = [RetrofitModule::class])
class ApiModule {
    @Provides
    fun provideUserApi(@Named("main-api") retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }
}