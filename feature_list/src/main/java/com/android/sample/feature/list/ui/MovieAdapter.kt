package com.android.sample.feature.list.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.sample.common.base.BaseAdapter
import com.android.sample.common.extension.layoutInflater
import com.android.sample.core.response.Movie
import com.android.sample.feature.list.databinding.MovieItemBinding

class MovieAdapter(
    private val callback: OnClickListener
) : BaseAdapter<Movie, MovieAdapter.MainViewHolder>(DiffCallback) {

    var baseImageUrl: String? = null

    /**
     * Called when RecyclerView needs a new {@movie ViewHolder} of the given type to represent
     * an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder.from(parent)

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position), callback, baseImageUrl)
    }

    /**
     * ViewHolder for movie items. All work is done by data binding.
     */
    class MainViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, callback: OnClickListener, baseImageUrl: String?) {
            with(binding) {
                this.baseImageUrl = baseImageUrl
                this.movie = movie
                this.callback = callback
            }
        }

        companion object {
            fun from(parent: ViewGroup): MainViewHolder {
                val binding = MovieItemBinding.inflate(
                    parent.context.layoutInflater,
                    parent,
                    false
                )
                return MainViewHolder(binding)
            }
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Movie]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(
            oldItem: Movie, newItem: Movie
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Movie, newItem: Movie
        ): Boolean = oldItem == newItem
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Movie]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Movie]
     */
    class OnClickListener(val clickListener: (link: Movie) -> Unit) {
        fun onClick(link: Movie) = clickListener(link)
    }
}