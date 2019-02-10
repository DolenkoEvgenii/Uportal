package etu.uportal

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.squareup.leakcanary.LeakCanary
import es.dmoral.toasty.Toasty
import etu.uportal.di.AppComponent
import etu.uportal.di.DaggerAppComponent
import etu.uportal.di.module.ApiModule
import etu.uportal.di.module.AppModule
import etu.uportal.di.module.RetrofitModule

open class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        initLogger()
        initToasty()
        //initFabric()

        component = buildComponent()
        MultiDex.install(this)

        if (BuildConfig.DEBUG) {
            LeakCanary.install(this)
        }
    }

    private fun initToasty() {
        Toasty.Config.getInstance()
                .setTextSize(15)
                .apply()
    }

    private fun initLogger() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(2)
                .methodOffset(7)
                .tag("Haglar_Log")
                .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }

/*    private fun initFabric() {
        if (BuildConfig.DEBUG) {
            Fabric.with(this, Answers())
        } else {
            Fabric.with(this, Answers(), Crashlytics())
        }
    }*/

    private fun buildComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .retrofitModule(RetrofitModule())
                .apiModule(ApiModule())
                .build()
    }

    companion object {
        @JvmStatic
        lateinit var component: AppComponent
    }
}
