package com.example.movie.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.MovieApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.movie.domain.Movie
import com.example.movie.service.MovieService
import com.example.movie.service.ServiceFactory

class MainModel : ViewModel() {

    private val service: MovieService = ServiceFactory
        .createRetrofitService(MovieService::class.java, MovieService.SERVICE_ENDPOINT)

    private val mutableMovies = MutableLiveData<List<Movie>>().apply { value = emptyList() }
    private val mutableLoading = MutableLiveData<Boolean>().apply { value = false }
    private val mutableMessage = MutableLiveData<String>()
    private var genres = emptyList<String>()

    val movies: LiveData<List<Movie>> = mutableMovies
    val loading: LiveData<Boolean> = mutableLoading
    val message: LiveData<String> = mutableMessage

    fun fetchMoviesFromNetwork(app: MovieApp) {
        mutableLoading.postValue(true)
        viewModelScope.launch {
            try {
                mutableMovies.value = service.getMovies()
                Log.d("tag", "get movies from service")
                launch(Dispatchers.IO) {
                    app.db.moviesDao.deleteMovies()
                    Log.d("tag", "delete movies from db")
                    app.db.moviesDao.addMovies(movies.value!!)
                    Log.d("tag", "add movies to db")
                }
            } catch (e: Exception) {
                mutableMessage.value = "Received an error while retrieving the data: ${e.message}"
            } finally {
                mutableLoading.value = false
            }
        }
    }

    fun fetchMovies(app: MovieApp) {
        mutableLoading.value = true
        try {
            GlobalScope.launch(Dispatchers.IO) {
                Log.d("tag", "get number of movies from db")
                val numberOfMovies = app.db.moviesDao.numberOfMovies
                if (numberOfMovies <= 0) {
                    fetchMoviesFromNetwork(app)
                }
            }
        } catch (e: Exception) {
            mutableMessage.value = "Received an error while retrieving local data: ${e.message}"
        } finally {
            mutableLoading.value = false
        }
    }

    fun addMovie(app: MovieApp, movie: Movie) {
        mutableLoading.value = true
        viewModelScope.launch {
            try {
                service.addMovie(movie)
                Log.d("tag", "new movie added in the service")
                launch(Dispatchers.IO) {
                    Log.d("tag", "new movie added in the database")
                    app.db.moviesDao.addMovie(movie)
                }
            } catch (e: Exception) {
                mutableMessage.value = "Received an error while adding the data: ${e.message}"
            } finally {
                mutableLoading.value = false
            }
        }
    }

    fun deleteMovie(app: MovieApp, id: Int) {
        mutableLoading.value = true
        viewModelScope.launch {
            try {
                service.deleteMovie(id)
                Log.d("tag", "movie deleted from service")
                launch(Dispatchers.IO) {
                    Log.d("tag", "movie deleted from database")
                    app.db.moviesDao.deleteById(id)
                }
            } catch (e: Exception) {
                mutableMessage.value = "Received an error while deleting the data: ${e.message}"
            } finally {
                mutableLoading.value = false
            }
        }
    }

    fun fetchGenresFromNetwork() {
        mutableLoading.postValue(true)
        viewModelScope.launch {
            try {
                genres = service.getGenres()
                Log.d("tag", "get genres from service")
            } catch (e: Exception) {
                mutableMessage.value = "Received an error while retrieving the data: ${e.message}"
            } finally {
                mutableLoading.value = false
            }
        }
    }

    fun updateMovie(app: MovieApp, movie: Movie) {
        mutableLoading.value = true
        viewModelScope.launch {
            try {
                service.updateMovieDetails(movie)
                Log.d("tag", "movie updated in the service")
                launch(Dispatchers.IO) {
                    Log.d("tag", "movie updated in the database")
                    app.db.moviesDao.updateMovie(movie)
                }
            } catch (e: Exception) {
                mutableMessage.value = "Received an error while adding the data: ${e.message}"
            } finally {
                mutableLoading.value = false
            }
        }
    }
}