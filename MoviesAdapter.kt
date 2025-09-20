package com.example.blastorform.movies

import android.util.Log // Added for Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.blastorform.R
import com.example.blastorform.movies.Movie

class MoviesAdapter(
    private var movies: List<Movie>,
    private val onMovieClickListener: ((Movie) -> Unit)? = null
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = movies.size

    fun updateData(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.movie_title_text_view)
        private val overviewTextView: TextView = itemView.findViewById(R.id.movie_overview_text_view)
        private val posterImageView: ImageView = itemView.findViewById(R.id.movie_poster_image_view)

        fun bind(movie: Movie) {
            titleTextView.text = movie.title
            overviewTextView.text = movie.overview

            Log.d("MoviesAdapter", "Binding movie: ${movie.title}")
            Log.d("MoviesAdapter", "Poster Path: ${movie.posterPath}") // Check if this is null/empty
            Log.d("MoviesAdapter", "Full Poster URL: ${movie.fullPosterUrl}") // Check the constructed URL

            Glide.with(itemView.context)
                .load(movie.fullPosterUrl)
                .placeholder(R.drawable.ic_launcher_background) // Optional: Add a placeholder
                .error(R.drawable.ic_launcher_foreground)       // Optional: Add an error image
                .into(posterImageView)
            
            itemView.setOnClickListener {
                onMovieClickListener?.invoke(movie)
            }
        }
    }
}
