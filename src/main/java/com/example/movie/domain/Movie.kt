package com.example.movie.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(@field:PrimaryKey(autoGenerate = true)
                    var id: Int, var title: String?, var description: String?, var genre: String?, var year: Int?, var rating: Int?, var length: Int?)
