package com.example.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_new_movie.*
import com.example.movie.domain.Movie
import com.example.movie.model.MainModel

class NewMovie : AppCompatActivity() {
    private lateinit var model: MainModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_movie)
        model = ViewModelProviders.of(this).get(MainModel::class.java)
        save.setOnClickListener {
            val app: MovieApp = application as MovieApp
            model.addMovie(app, Movie(0, movieTitle.text.toString(), movieDescription.text.toString()
            , movieGenre.text.toString(), movieYear.text.toString().toInt(), movieRating.text.toString().toInt(), movieLength.text.toString().toInt()))
            finish()
        }
    }
}
