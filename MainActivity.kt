package com.example.blastorform

import android.content.Intent // Added for Intent
import android.os.Bundle
import android.widget.Button // Added for Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.blastorform.movies.MoviesActivity // Added for MoviesActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val mainLayout = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.main) // Assuming R.id.main is the ConstraintLayout
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val showMoviesButton: Button = findViewById(R.id.show_movies_button)
        showMoviesButton.setOnClickListener {
            val intent = Intent(this, MoviesActivity::class.java)
            startActivity(intent)
        }
    }
}
