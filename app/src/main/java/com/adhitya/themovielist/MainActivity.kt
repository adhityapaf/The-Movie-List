package com.adhitya.themovielist

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.adhitya.themovielist.BuildConfig.API_KEY
import com.adhitya.themovielist.databinding.ActivityMainBinding
import com.adhitya.themovielist.genre.GenreAdapter
import com.adhitya.themovielist.genre.GenreMoviesAdapter
import com.adhitya.themovielist.model.GenreMoviesResponse
import com.adhitya.themovielist.model.GenreResponse
import com.adhitya.themovielist.model.NowPlayingMoviesResponse
import com.adhitya.themovielist.now_playing.NowPlayingMoviesAdapter
import com.adhitya.themovielist.service.ApiConfig
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity(), GenreAdapter.GenreCallback {

    companion object {
        const val TAG = "MainActivity"
    }
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.displayOptions = androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.action_bar)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFF")))
        with(binding.rvGenreList) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
        loadNowPlayingMovies()
        loadGenres()
        binding.rvGenreMovies.layoutManager = GridLayoutManager(this@MainActivity, 2)
    }

    private fun loadGenres() {
        // insert loading
        val client = ApiConfig.getApiService().getGenreMovies(API_KEY)
        client.enqueue(object: retrofit2.Callback<GenreResponse> {
            override fun onResponse(call: Call<GenreResponse>, response: Response<GenreResponse>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse Genre: ${Gson().toJson(response.body())}")
                    val resBody = response.body()
                    binding.rvGenreList.adapter = GenreAdapter(resBody, this@MainActivity)
                    binding.spGenres.visibility = View.GONE
                } else {
                    Log.d(TAG, "onResponseFailure Genre: ${Gson().toJson(response.message())}")
                    showToastError(response.message())
                }
            }

            override fun onFailure(call: Call<GenreResponse>, t: Throwable) {
                Log.d(TAG, "onFailure Genre: ${t.message.toString()}")
                showToastError(t.message.toString())
            }

        })
    }

    private fun loadNowPlayingMovies() {
        // insert loading here after success test laod api
        val client = ApiConfig.getApiService().getNowPlayingMovies(API_KEY)
        client.enqueue(object: retrofit2.Callback<NowPlayingMoviesResponse> {
            override fun onResponse(
                call: Call<NowPlayingMoviesResponse>,
                response: Response<NowPlayingMoviesResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse NPM : ${Gson().toJson(response.body())}")
                    val responseBody = response.body()
                    binding.csiIndicator.indicatorsToShow = responseBody?.results?.size!!
                    binding.csvwNowPlayingMovies.adapter = NowPlayingMoviesAdapter(responseBody)
                    binding.spNpm.visibility = View.GONE
                } else {
                    Log.d(TAG, "onResponseFailure MPM: ${response.message()}")
                    showToastError(response.message())
                }
            }

            override fun onFailure(call: Call<NowPlayingMoviesResponse>, t: Throwable) {
                Log.d(TAG, "onFailure NPM: ${t.message.toString()}")
                showToastError(t.message.toString())
            }

        })
    }

    private fun showToastError(errorMsg: String) {
        Toast.makeText(this@MainActivity, "Terjadi Kesalahan: $errorMsg", Toast.LENGTH_SHORT).show()
    }

    override fun resultCallback(intent: Intent) {
        val id = intent.getIntExtra("genreId", 0)
        val client = ApiConfig.getApiService().getGenreMoviesList(API_KEY, id)
        client.enqueue(object: retrofit2.Callback<GenreMoviesResponse> {
            override fun onResponse(
                call: Call<GenreMoviesResponse>,
                response: Response<GenreMoviesResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse Genre Movies : ${Gson().toJson(response.body())}")
                    val responseBody = response.body()
                    binding.rvGenreMovies.adapter = GenreMoviesAdapter(responseBody)
                    binding.spMovies.visibility = View.GONE
                } else {
                    Log.d(TAG, "onResponseFailure Genre Movies: ${response.message()}")
                    showToastError(response.message())
                }
            }

            override fun onFailure(call: Call<GenreMoviesResponse>, t: Throwable) {
                Log.d(TAG, "onFailure Genre Movies: ${t.message.toString()}")
                showToastError(t.message.toString())
            }

        })
    }
}