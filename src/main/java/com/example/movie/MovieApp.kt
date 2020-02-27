package com.example.movie

import android.app.Application
import androidx.room.Room

import com.example.movie.db.MovieDatabase

class MovieApp : Application() {
    lateinit var db: MovieDatabase

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder<MovieDatabase>(applicationContext,
            MovieDatabase::class.java, "database-name").build()
    }
}
