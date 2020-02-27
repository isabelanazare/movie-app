package com.example.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.movie_detail.view.*
import com.example.movie.domain.Movie
import com.example.movie.model.MainModel

class EventDetailFragment : Fragment() {

    private lateinit var movie: Movie
    private lateinit var model: MainModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null && arguments!!.containsKey(ARG_ITEM_ID)) {
            val movieId = arguments!!.getInt(ARG_ITEM_ID)
            val movieTitle = arguments!!.getString(ARG_ITEM_TITLE)
            val movieDescription = arguments!!.getString(ARG_ITEM_DESCRIPTON)
            val movieGenre = arguments!!.getString(ARG_ITEM_GENRE)
            val movieYear = arguments!!.getString(ARG_ITEM_YEAR)
            val movieRating = arguments!!.getString(ARG_ITEM_RATING)
            val movieLength = arguments!!.getString(ARG_ITEM_LENGTH)

            movie = Movie(movieId, movieTitle,movieDescription, movieGenre, movieYear!!.toInt(), movieRating!!.toInt(), movieLength!!.toInt())
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.movie_detail, container, false)

        rootView.event_detail.text = movie.title

        return rootView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        model = ViewModelProviders.of(this).get(MainModel::class.java)
    }
    companion object {
        const val ARG_ITEM_ID = "item_id"
        const val ARG_ITEM_TITLE = "item_title"
        const val ARG_ITEM_DESCRIPTON = "item_description"
        const val ARG_ITEM_GENRE = "item_genre"
        const val ARG_ITEM_YEAR = "item_year"
        const val ARG_ITEM_RATING = "item_rating"
        const val ARG_ITEM_LENGTH = "item_length"
    }
}
