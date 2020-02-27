package com.example.movie.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.EventDetailActivity
import com.example.movie.EventDetailFragment
import com.example.movie.R
import com.example.movie.domain.Movie
import kotlinx.android.synthetic.main.item_view.view.*

class Top10Adapter : RecyclerView.Adapter<Top10Adapter.ElementViewAdapter>() {
    private var mValues = mutableListOf<Movie>()

    fun setData(movies: List<Movie>) {
        val sortedMovies = movies.sortedByDescending { myObject -> myObject.rating}

        mValues.clear()
        mValues.addAll(sortedMovies.take(10))
        notifyDataSetChanged()
    }

    class ElementViewAdapter(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewAdapter {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        return ElementViewAdapter(view)
    }

    override fun onBindViewHolder(holder: ElementViewAdapter, position: Int) {
        val movie = mValues[position]

        holder.view.title.text = movie.title
        holder.view.Year.text = movie.year.toString()

        holder.view.setOnClickListener { v ->
            val context = v.context
            val intent = Intent(context, EventDetailActivity::class.java)
            val args = Bundle()
            args.putInt(EventDetailFragment.ARG_ITEM_ID, movie.id)
            args.putString(EventDetailFragment.ARG_ITEM_TITLE, movie.title)
            args.putString(EventDetailFragment.ARG_ITEM_DESCRIPTON, movie.description)
            args.putString(EventDetailFragment.ARG_ITEM_GENRE, movie.genre)
            args.putString(EventDetailFragment.ARG_ITEM_YEAR, movie.year.toString())
            args.putString(EventDetailFragment.ARG_ITEM_RATING, movie.rating.toString())
            args.putString(EventDetailFragment.ARG_ITEM_LENGTH, movie.length.toString())

            intent.putExtras(args)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }
}
