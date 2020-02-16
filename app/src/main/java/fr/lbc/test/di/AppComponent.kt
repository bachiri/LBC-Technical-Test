package fr.lbc.test.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import fr.lbc.test.album.view.AlbumActivity
import fr.lbc.test.albums.view.AlbumsActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        ViewModelModule::class,
        DataBaseModule::class,
        RepositoryModule::class,
        DomainModule::class
    ]
)
interface AppComponent {

    fun inject(albumsActivity: AlbumsActivity)

    fun inject(mainActivity: AlbumActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}