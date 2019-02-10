package etu.uportal.di.module

import dagger.Module
import dagger.Provides
import etu.uportal.di.scope.ExampleScope
import etu.uportal.model.interactor.ExampleInteractor

@Module
class ExampleModule {
    @Provides
    @ExampleScope
    fun provideExampleInteractor(): ExampleInteractor {
        return ExampleInteractor()
    }
}
