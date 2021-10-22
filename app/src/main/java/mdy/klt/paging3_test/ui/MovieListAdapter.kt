package mdy.klt.paging3_test.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mdy.klt.paging3_test.databinding.ItemMovieBinding
import mdy.klt.paging3_test.helper.Endpoints
import mdy.klt.paging3_test.model.Movie

class MovieListAdapter(
    private val onClick: (Int) -> Unit
) : PagingDataAdapter<Movie, RecyclerView.ViewHolder>(MovieComparator) {

    fun getClickItem(position: Int): Movie? = getItem(position)

    object MovieComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.movieId == newItem.movieId

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie?) = with(binding) {
            item?.let {
                this.title.text = item.original_title
                this.date.text = item.release_date
                Glide.with(itemView.context).load(Endpoints.IMAGE_URL + item.backdrop_path)
                    .into(this.img)
            }
            itemView.setOnClickListener { onClick }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieViewHolder).bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }
}