package etu.uportal.model.manager

import etu.uportal.App
import etu.uportal.di.ExampleComponent

object InjectionManager {
    private var exampleComponent: ExampleComponent? = null

    val exampleInteractorComponent: ExampleComponent
        get() {
            if (exampleComponent == null) {
                exampleComponent = App.component
                        .exampleComponentBuilder()
                        .build()
            }
            return exampleComponent!!
        }

    fun clearExampleInteractorComponent() {
        exampleComponent = null
    }
}
