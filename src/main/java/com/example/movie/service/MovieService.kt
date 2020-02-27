package com.example.movie.service

import com.example.movie.domain.Movie
import io.reactivex.Completable
import retrofit2.http.*


interface MovieService {

    @GET("/movies")
    suspend fun getMovies(): List<Movie>

    @GET("/details/{id}")
    suspend fun getDetails(@Path("id") id: Int): Movie

    @POST("/update")
    suspend fun updateMovieDetails(@Body e: Movie):  Completable

    @POST("/add")
    suspend fun addMovie(@Body e: Movie): Movie

    @DELETE("delete/{id}")
    suspend  fun deleteMovie(@Path("id") id: Int) : Completable

    @GET("/genres")
    suspend fun getGenres(): List<String>

    companion object {
        const val SERVICE_ENDPOINT = "http://10.0.2.2:2020"
    }
}
