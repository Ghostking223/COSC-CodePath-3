package com.example.blastorform.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.blastorform.R // For R.layout.activity_movies

class MoviesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        // The FragmentContainerView in activity_movies.xml handles loading MoviesFragment
        // so no explicit fragment transaction is needed here if using android:name attribute.
    }
}
