package com.adhitya.themovielist.service

import com.adhitya.themovielist.model.GenreMoviesResponse
import com.adhitya.themovielist.model.GenreResponse
import com.adhitya.themovielist.model.NowPlayingMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key")
        api_key: String
    ) : Call<NowPlayingMoviesResponse>

    @GET("genre/movie/list")
    fun getGenreMovies(
        @Query("api_key")
        api_key: String
    ) : Call<GenreResponse>

    @GET("discover/movie")
    fun getGenreMoviesList(
        @Query ("api_key")
        api_key: String,
        @Query ("with_genres")
        with_genres: Int
    ) : Call<GenreMoviesResponse>
}