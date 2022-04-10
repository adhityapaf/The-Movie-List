package com.adhitya.themovielist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.adhitya.themovielist.BuildConfig.API_KEY
import com.adhitya.themovielist.databinding.ActivityMainBinding
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
        loadNowPlayingMovies()

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
                    Toast.makeText(this@MainActivity, "Terjadi Kesalahan: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<NowPlayingMoviesResponse>, t: Throwable) {
                Log.d(TAG, "onFailure NPM: ${t.message.toString()}")
                Toast.makeText(this@MainActivity, "Terjadi kesalahan: "+t.message.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }


}