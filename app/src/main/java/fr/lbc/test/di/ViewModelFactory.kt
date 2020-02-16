package fr.lbc.test.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import fr.lbc.test.album.view.AlbumViewModel
import fr.lbc.test.albums.view.AlbumsViewModel
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

//Reference https://proandroiddev.com/viewmodel-with-dagger2-architecture-components-2e06f06c9455
@Singleton
class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        viewModels[modelClass]?.get() as T
}

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AlbumsViewModel::class)
    internal abstract fun AlbumsViewModel(viewModel: AlbumsViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(AlbumViewModel::class)
    internal abstract fun AlbumViewModel(viewModel: AlbumViewModel): ViewModel


}