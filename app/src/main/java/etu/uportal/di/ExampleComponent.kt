package etu.uportal.di

import dagger.Subcomponent
import etu.uportal.di.module.ExampleModule
import etu.uportal.di.scope.ExampleScope

@Subcomponent(modules = [ExampleModule::class])
@ExampleScope
interface ExampleComponent {

    @Subcomponent.Builder
    interface Builder {
        fun authModule(exampleModule: ExampleModule): Builder

        fun build(): ExampleComponent
    }
}