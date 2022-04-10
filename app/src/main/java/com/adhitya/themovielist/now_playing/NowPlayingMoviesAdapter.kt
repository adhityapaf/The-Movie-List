package com.adhitya.themovielist.now_playing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.adhitya.themovielist.R
import com.adhitya.themovielist.model.NowPlayingMoviesResponse
import com.bumptech.glide.Glide
import com.github.islamkhsh.CardSliderAdapter

class NowPlayingMoviesAdapter(private val npMovies: NowPlayingMoviesResponse?) : CardSliderAdapter<NowPlayingMoviesAdapter.NowPlayingMoviesViewHolder>() {
    class NowPlayingMoviesViewHolder (view: View) : RecyclerView.ViewHolder(view){
        var npMoviesImage : ImageView = view.findViewById(R.id.iv_item_now_playing_movies)
    }

    override fun bindVH(holder: NowPlayingMoviesAdapter.NowPlayingMoviesViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500"+npMovies?.results?.get(position)?.backdropPath)
            .centerCrop()
            .into(holder.npMoviesImage)
    }

    override fun getItemCount(): Int {
        var size = 0
        if (npMovies != null) {
            size = npMovies.results?.size!!
        }
        return size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NowPlayingMoviesAdapter.NowPlayingMoviesViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_now_playing_movies, parent, false)
        return NowPlayingMoviesViewHolder(view)
    }
}