package com.example.movie

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.example.movie.adapter.DirectorAdapter
import com.example.movie.domain.Movie
import com.example.movie.model.MainModel
import com.example.movie.utils.logd
import kotlinx.android.synthetic.main.director_movie_list.*

class DirectorActivity : AppCompatActivity() {

    private var adapter: DirectorAdapter? = null

    private lateinit var manager: Manager
    private lateinit var model: MainModel

    private fun checkIfOnline(): Boolean{
        if(!manager.networkConnectivity(applicationContext)){
            Toast.makeText(this, "You don't have internet connection", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.director_movie_list)
        model = ViewModelProviders.of(this).get(MainModel::class.java)
        manager = Manager()

        refresh.setOnClickListener {
            model.fetchMoviesFromNetwork(application as MovieApp)
        }

        fab.setOnClickListener {
            val intent = Intent(application, NewMovie::class.java)
            startActivityForResult(intent, 10000)
        }

        if(checkIfOnline()) {

            setSupportActionBar(toolbar)
            toolbar.title = title

            assert(recyclerView != null)
            setupRecyclerView(recyclerView)
            observeModel()
            loadMovies()
        }
    }

    private fun observeModel() {
        model.loading.observe { displayLoading(it) }
        model.movies.observe { displayMovies(it ?: emptyList()) }
        model.message.observe { showError(it) }
    }

    private fun displayMovies(movies: List<Movie>) {
        adapter?.setData(movies)
    }

    private fun displayLoading(loading: Boolean?) {
        logd("displayLoading: $loading")
        progress.visibility = if (loading!!) View.VISIBLE else View.GONE
    }

    private fun <T> LiveData<T>.observe(observe: (T?) -> Unit) =
        observe(this@DirectorActivity, Observer { observe(it) })

    private fun loadMovies() {
        model.fetchMovies(application as MovieApp)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        logd("Back in main activity")
    }


    fun showError(message: String?) {
        progress.visibility = View.GONE
        var errorMessage = "Unknown error"
        if (message != null) {
            errorMessage = message
        }
        Snackbar.make(recyclerView!!, errorMessage, Snackbar.LENGTH_INDEFINITE)
            .setAction("RETRY") { loadMovies() }.show()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        adapter = DirectorAdapter()
        (application as MovieApp).db.moviesDao.movies
            .observe(this, Observer { movies ->
                if (movies != null) {
                    adapter!!.setData(movies)
                }
            })
        recyclerView.adapter = adapter
    }
}
