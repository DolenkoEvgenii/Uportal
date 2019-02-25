package etu.uportal.di


import android.content.Context
import dagger.Component
import etu.uportal.di.module.ApiModule
import etu.uportal.di.module.AppModule
import etu.uportal.di.module.CiceroneModule
import etu.uportal.di.module.RetrofitModule
import etu.uportal.model.network.BaseRepository
import etu.uportal.presentation.presenter.SimplePresenter
import etu.uportal.presentation.presenter.auth.LoginPresenter
import etu.uportal.ui.activity.base.BaseMvpFragmentActivity
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Component(modules = [AppModule::class, RetrofitModule::class, ApiModule::class, CiceroneModule::class])
@Singleton
interface AppComponent {
    fun context(): Context

    fun router(): Router


    fun inject(utilityWrapper: BaseRepository.ApiUtilityWrapper)

    fun exampleComponentBuilder(): ExampleComponent.Builder

    fun inject(baseActivity: BaseMvpFragmentActivity)

    fun inject(presenter: SimplePresenter)

    fun inject(presenter: LoginPresenter)
}
