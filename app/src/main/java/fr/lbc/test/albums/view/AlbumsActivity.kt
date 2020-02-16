package fr.lbc.test.albums.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import fr.lbc.test.data.Album
import kotlinx.android.synthetic.main.activity_albums.*
import javax.inject.Inject
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.lbc.test.AlbumApplication
import fr.lbc.test.R
import fr.lbc.test.album.view.AlbumActivity


class AlbumsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var albumsViewModel: AlbumsViewModel

    private val onItemClickListener: AlbumItemClickListener = object : AlbumItemClickListener {
        override fun onAlbumItemClicked(id: Int) {
            val intent = Intent(this@AlbumsActivity, AlbumActivity::class.java)
            intent.putExtra(AlbumActivity.EXTRA_ALBUM_ID, id)
            startActivity(intent)
        }
    }

    private val albumsAdapter: AlbumsAdapter =
        AlbumsAdapter(this@AlbumsActivity, mutableListOf(), onItemClickListener)


    override fun onCreate(savedInstanceState: Bundle?) {
        (application as AlbumApplication).albumAppComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)
        initRecyclerView()
        initViewModel()

    }

    private fun initViewModel() {
        albumsViewModel = ViewModelProvider(this, viewModelFactory).get(AlbumsViewModel::class.java)
        albumsViewModel.albumList.observe(this, Observer { albumViewState ->
            when (albumViewState) {
                is AlbumsOnSuccess -> {
                    displayAlbums(albumViewState.data)
                }
                is AlbumsOnError -> {
                    Toast.makeText(this@AlbumsActivity, albumViewState.error, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })

        albumsViewModel.loading.observe(this, Observer {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    private fun initRecyclerView() {
        val gridLayoutManager = GridLayoutManager(recyclerView.context, 2)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = albumsAdapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (gridLayoutManager.findLastVisibleItemPosition() == gridLayoutManager.itemCount - 1) {
                    Toast.makeText(this@AlbumsActivity, "Next Page", Toast.LENGTH_SHORT).show()
                    albumsViewModel.nextPage()
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

    }

    private fun displayAlbums(albums: List<Album>) {
        albumsAdapter.setData(albums)
    }
}

sealed class AlbumsViewState
data class AlbumsOnSuccess(var data: List<Album>) : AlbumsViewState()
data class AlbumsOnError(var error: String) : AlbumsViewState()



