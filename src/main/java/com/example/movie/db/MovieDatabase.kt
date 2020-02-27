package com.example.movie.db

import androidx.room.Database
import androidx.room.RoomDatabase

import com.example.movie.domain.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract val moviesDao: MoviesDao
}
