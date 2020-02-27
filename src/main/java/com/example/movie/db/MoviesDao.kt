package com.example.movie.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.movie.domain.Movie

@Dao
interface MoviesDao {

    @get:Query("select * from movies")
    val movies: LiveData<MutableList<Movie>>

    @get:Query("select count(*) from movies")
    val numberOfMovies: Int

    @Insert
    fun addMovie(movie: Movie)

    @Insert
    fun addMovies(products: List<Movie>)

    @Delete
    fun deleteMovie(m: Movie)

    @Query("delete from movies")
    fun deleteMovies()

    @Update
    fun updateMovie(m: Movie)

    @Query("SELECT * FROM movies WHERE title LIKE :title")
    fun findByTitle(title: String): Movie

    @Query("DELETE FROM movies WHERE id = :movieId")
    fun deleteById(movieId: Int)
}
