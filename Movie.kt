package com.example.blastorform.movies

import com.google.gson.annotations.SerializedName

// Represents the overall API response from TMDB for /movie/now_playing
data class MovieResponse(
    @SerializedName("page")
    val page: Int?,

    @SerializedName("results")
    val results: List<Movie>?,

    @SerializedName("total_pages")
    val totalPages: Int?,

    @SerializedName("total_results")
    val totalResults: Int?
)

// Represents a single Movie item
data class Movie(
    @SerializedName("id")
    val id: Long,

    @SerializedName("title")
    val title: String?,

    @SerializedName("overview")
    val overview: String?, // This will serve as the description

    @SerializedName("poster_path")
    val posterPath: String?, // Relative path, e.g., "/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg"

    @SerializedName("backdrop_path")
    private val backdropPath: String? // Relative path for a backdrop image, optional
) {
    // Read-only property to construct the full poster image URL
    val fullPosterUrl: String?
        get() = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" }

    // Read-only property to construct the full backdrop image URL (larger width, e.g., w780)
    val fullBackdropUrl: String?
        get() = backdropPath?.let { "https://image.tmdb.org/t/p/w780$it" }
}
