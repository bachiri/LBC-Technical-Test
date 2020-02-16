package fr.lbc.test.album.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import fr.lbc.test.AlbumApplication
import fr.lbc.test.R
import fr.lbc.test.data.Album
import kotlinx.android.synthetic.main.activity_album.*
import javax.inject.Inject

class AlbumActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var albumViewModel: AlbumViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as AlbumApplication).albumAppComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)
        val albumId = intent.getIntExtra(EXTRA_ALBUM_ID, -1)
        albumViewModel = ViewModelProvider(this, viewModelFactory).get(AlbumViewModel::class.java)
        albumViewModel.start(albumId)
        setUpViewObservers()

    }

    private fun setUpViewObservers() {
        albumViewModel.album.observe(this, Observer { albumViewState ->
            when (albumViewState) {
                is AlbumOnSuccess -> {
                    displayAlbum(albumViewState.data)
                }
                is AlbumOnError -> {
                    errorLabel.text = albumViewState.error
                }
            }
        })

        albumViewModel.errorVisibility.observe(this, Observer {
            errorLabel.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    @SuppressLint("SetTextI18n")
    private fun displayAlbum(album: Album) {
        Picasso.get().load(album.url).into(albumImage)
        id.text = "Id :" + album.id.toString()
        albumId.text = "Album Id:" + album.albumId.toString()
        albumTitle.text = "Title :" + album.title
    }

    companion object {
        const val EXTRA_ALBUM_ID = "fr.lbc.test.album.view.AlbumActivity.id"
    }
}