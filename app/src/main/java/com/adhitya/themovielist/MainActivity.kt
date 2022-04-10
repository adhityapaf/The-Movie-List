package com.adhitya.themovielist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adhitya.themovielist.BuildConfig.API_KEY
import com.adhitya.themovielist.databinding.ActivityMainBinding
import com.adhitya.themovielist.genre.GenreAdapter
import com.adhitya.themovielist.model.GenreResponse
import com.adhitya.themovielist.model.NowPlayingMoviesResponse
import com.adhitya.themovielist.now_playing.NowPlayingMoviesAdapter
import com.adhitya.themovielist.service.ApiConfig
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

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
        with(binding.rvGenreList) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
        loadNowPlayingMovies()
        loadGenres()
    }

    private fun loadGenres() {
        // insert loading
        val client = ApiConfig.getApiService().getGenreMovies(API_KEY)
        client.enqueue(object: retrofit2.Callback<GenreResponse> {
            override fun onResponse(call: Call<GenreResponse>, response: Response<GenreResponse>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse Genre: ${Gson().toJson(response.body())}")
                    val resBody = response.body()
                    binding.rvGenreList.layoutManager
                    binding.rvGenreList.adapter = GenreAdapter(resBody, this@MainActivity)
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
}