package com.example.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.movie.domain.Movie
import com.example.movie.model.MainModel
import kotlinx.android.synthetic.main.activity_new_movie.movieDescription
import kotlinx.android.synthetic.main.activity_new_movie.movieGenre
import kotlinx.android.synthetic.main.activity_new_movie.movieLength
import kotlinx.android.synthetic.main.activity_new_movie.movieRating
import kotlinx.android.synthetic.main.activity_new_movie.movieTitle
import kotlinx.android.synthetic.main.activity_new_movie.movieYear
import kotlinx.android.synthetic.main.activity_update_movie.*

class UpdateMovie : AppCompatActivity() {
    private lateinit var model: MainModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_movie)
        model = ViewModelProviders.of(this).get(MainModel::class.java)
        val bundle :Bundle ?=intent.extras
        val movieId = bundle!!.getString("movie_id")

        updateButton.setOnClickListener {
            val app: MovieApp = application as MovieApp

           model.updateMovie(app, Movie(movieId!!.toInt(), movieTitle.text.toString(), movieDescription.text.toString()
               , movieGenre.text.toString(), movieYear.text.toString().toInt(), movieRating.text.toString().toInt(), movieLength.text.toString().toInt()))
            finish()
        }
    }
}
