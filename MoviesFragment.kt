package com.example.blastorform.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.blastorform.R // For R.layout.fragment_movies_list etc.
// Make sure these local classes are imported if they are not in the same package
import com.example.blastorform.movies.Movie
import com.example.blastorform.movies.MovieResponse
import com.example.blastorform.movies.MoviesAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers

private const val TMDB_API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"
private const val NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing"
private const val TAG = "MoviesFragment"

class MoviesFragment : Fragment() {

    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var progressBar: ContentLoadingProgressBar
    private var moviesList: MutableList<Movie> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)

        moviesRecyclerView = view.findViewById(R.id.movies_recycler_view)
        progressBar = view.findViewById(R.id.movies_progress_bar)

        moviesAdapter = MoviesAdapter(moviesList) { movie ->
            Toast.makeText(context, "Clicked: ${movie.title}", Toast.LENGTH_SHORT).show()
        }
        moviesRecyclerView.layoutManager = LinearLayoutManager(context)
        moviesRecyclerView.adapter = moviesAdapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchMovies()
    }

    private fun fetchMovies() {
        progressBar.show()

        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api_key"] = TMDB_API_KEY

        client.get(NOW_PLAYING_URL, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                progressBar.hide()
                Log.d(TAG, "API Response: ${json.jsonObject.toString()}")

                try {
                    val gson = Gson()
                    val movieResponseType = object : TypeToken<MovieResponse>() {}.type
                    val movieResponse: MovieResponse? = gson.fromJson(json.jsonObject.toString(), movieResponseType)
                    
                    movieResponse?.results?.let {
                        // Clear previous movies before adding new ones if you intend to refresh
                        // moviesList.clear() 
                        moviesList.addAll(it)
                        // It's better to submit a new list to the adapter if using DiffUtil,
                        // or for simplicity, update the existing list and notify.
                        moviesAdapter.updateData(moviesList) 
                        Log.d(TAG, "Movies fetched and adapter updated: ${moviesList.size} movies")
                    } ?: run {
                        Log.e(TAG, "No results found in API response or error parsing results.")
                        Toast.makeText(context, "No movies found or error parsing.", Toast.LENGTH_LONG).show()
                    }

                } catch (e: Exception) {
                    Log.e(TAG, "JSON parsing error: ", e)
                    Toast.makeText(context, "Error parsing movie data.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?) {
                progressBar.hide()
                Log.e(TAG, "API call failed: Status Code: $statusCode, Response: $response", throwable)
                Toast.makeText(context, "Failed to fetch movies. Check logs.", Toast.LENGTH_LONG).show()
            }
        })
    }
}
