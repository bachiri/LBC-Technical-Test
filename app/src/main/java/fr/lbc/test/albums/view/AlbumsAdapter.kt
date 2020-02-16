package fr.lbc.test.albums.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.lbc.test.data.Album
import android.view.LayoutInflater
import com.squareup.picasso.Picasso
import fr.lbc.test.R
import kotlinx.android.synthetic.main.album_item.view.*


class AlbumsAdapter(
    private val context: Context,
    private var albums: List<Album>,
    private val listener: AlbumItemClickListener
) : RecyclerView.Adapter<AlbumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.album_item, parent, false)
        return AlbumViewHolder(view)
    }

    fun setData(albums: List<Album>) {
        this.albums = albums
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int = albums.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val currentAlbum = albums[position]
        holder.bind(currentAlbum, listener)
    }

}


class AlbumViewHolder(var viewItem: View) : RecyclerView.ViewHolder(viewItem) {

    lateinit var listener: AlbumItemClickListener

    fun bind(album: Album, listener: AlbumItemClickListener) {
        this.listener = listener
        val itemLabel = "${album.id} ${album.title}"
        viewItem.title.text = itemLabel
        Picasso.get().load(album.thumbnailUrl).into(viewItem.albumImage)
        viewItem.albumItem.setOnClickListener {
            listener.onAlbumItemClicked(album.id)
        }
    }
}


interface AlbumItemClickListener {
    fun onAlbumItemClicked(id: Int)
}
