package com.example.movie

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movie.adapter.ActorAdapter
import com.example.movie.model.MainModel
import kotlinx.android.synthetic.main.actor_options.*

class ActorOptionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actor_options)

        firstButton.setOnClickListener {
            startActivity(Intent(this, ActorActivity::class.java))
        }

        thirdButton.setOnClickListener {
            startActivity(Intent(this, Top10MoviesActivity::class.java))
        }

    }


}
