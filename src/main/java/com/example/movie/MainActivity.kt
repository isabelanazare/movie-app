package com.example.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        directorButton.setOnClickListener {
            startActivity(Intent(this, DirectorActivity::class.java))
        }

        actorButton.setOnClickListener {
            startActivity(Intent(this, ActorOptionsActivity::class.java))
        }
    }

}