package fr.lbc.test

import android.app.Application
import fr.lbc.test.di.AppComponent
import fr.lbc.test.di.DaggerAppComponent

class AlbumApplication : Application() {

    val albumAppComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
}