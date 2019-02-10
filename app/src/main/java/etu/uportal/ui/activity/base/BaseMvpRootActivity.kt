package etu.uportal.ui.activity.base

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import etu.uportal.R
import etu.uportal.presentation.presenter.SimplePresenter
import etu.uportal.presentation.view.BaseMvpView

abstract class BaseMvpRootActivity : BaseMvpFragmentActivity(), BaseMvpView {

    @InjectPresenter
    lateinit var presenter: SimplePresenter

    @ProvidePresenter
    abstract fun providePresenter(): SimplePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple)
    }
}
