package com.adhitya.themovielist.genre

import android.os.Build
import android.text.format.DateFormat.format
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.adhitya.themovielist.R
import com.adhitya.themovielist.model.GenreMoviesResponse
import com.bumptech.glide.Glide
import com.google.gson.internal.bind.util.ISO8601Utils.format
import java.lang.String.format
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class GenreMoviesAdapter (private val gMovies: GenreMoviesResponse?) : RecyclerView.Adapter<GenreMoviesAdapter.GenreMoviesViewHolder> () {
    class GenreMoviesViewHolder (view: View) : RecyclerView.ViewHolder(view){
        var movieImage : ImageView = view.findViewById(R.id.iv_item_genre_movies)
        var movieTitle : TextView = view.findViewById(R.id.tv_movie_name)
        var movieDate : TextView = view.findViewById(R.id.tv_movie_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreMoviesViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_genre_moives, parent, false)
        return GenreMoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreMoviesViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500"+gMovies?.results?.get(position)?.posterPath)
            .centerCrop()
            .into(holder.movieImage)
        holder.movieTitle.text = gMovies?.results?.get(position)?.title
        var date : String = gMovies?.results?.get(position)?.releaseDate.toString()
        holder.movieDate.text = date
    }

    override fun getItemCount(): Int = gMovies?.results?.size!!

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(date:String) : String {
        val dateAfter: String
        val dateBefore = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH)
        dateAfter = dateBefore.format(formatter)
        return dateAfter
    }
}