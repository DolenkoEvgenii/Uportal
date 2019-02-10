package etu.uportal.di.module

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val appContext: Context) {
    @Provides
    @Singleton
     fun provideContext(): Context {
        return appContext
    }

    @Provides
    @Singleton
     fun provideResources(): Resources {
        return appContext.resources
    }

    @Provides
    @Singleton
     fun provideDisplayMetrics(): DisplayMetrics {
        return appContext.resources.displayMetrics
    }
}
