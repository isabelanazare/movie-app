package com.example.movie

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NavUtils
import androidx.lifecycle.ViewModelProviders
import com.example.movie.model.MainModel
import kotlinx.android.synthetic.main.activity_movie_detail.*

class EventDetailActivity : AppCompatActivity() {
    private lateinit var model: MainModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        val toolbar = findViewById<Toolbar>(R.id.detail_toolbar)
        setSupportActionBar(toolbar)

        model = ViewModelProviders.of(this).get(MainModel::class.java)

        fab.setOnClickListener {
            val id = intent.extras!!.getInt("item_id").toString()
            val intent = Intent(this, UpdateMovie::class.java)
            intent.putExtra("movie_id",id)
            startActivity(intent)

        }

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            val bundle = intent.extras
            val fragment = EventDetailFragment()
            fragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .add(R.id.event_detail_container, fragment)
                .commit()
        }

      btnDelete2.setOnClickListener{
          model.deleteMovie(application as MovieApp, intent.extras!!.getInt("item_id"))
      }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, Intent(this, DirectorActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
