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
import etu.uportal.presentation.presenter.author.AuthorListPresenter
import etu.uportal.presentation.presenter.menu.MenuPresenter
import etu.uportal.presentation.presenter.user.UserListPresenter
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

    fun inject(presenter: MenuPresenter)

    fun inject(presenter: UserListPresenter)

    fun inject(presenter: AuthorListPresenter)
}
