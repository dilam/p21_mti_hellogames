package com.example.hellogames

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())

        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()

        val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)

        val callback : Callback<List<GameList>> = object : Callback<List<GameList>> {
            override fun onFailure(call: Call<List<GameList>>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed to load data", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<GameList>>, response: Response<List<GameList>>) {
                if (response.code() == 200)
                {
                    val responseData = response.body()

                    if (responseData != null) {

                        val s: MutableSet<Int> = mutableSetOf()
                        val maxSize = responseData.size - 1
                        while (s.size < 4) {
                            s.add((0..maxSize).random())
                        }
                        val rands = s.toList()

                        val game1 = responseData[rands[0]]
                        val game2 = responseData[rands[1]]
                        val game3 = responseData[rands[2]]
                        val game4 = responseData[rands[3]]

                        Glide.with(applicationContext)
                            .load(game1.picture)
                            .into(main_img_1)
                        Glide.with(applicationContext)
                            .load(game2.picture)
                            .into(main_img_2)
                        Glide.with(applicationContext)
                            .load(game3.picture)
                            .into(main_img_3)
                        Glide.with(applicationContext)
                            .load(game4.picture)
                            .into(main_img_4)

                        main_img_1.setOnClickListener {
                            val explicitIntent = Intent(applicationContext, DetailsActivity::class.java)
                            val gameId = game1.id
                            explicitIntent.putExtra("game_id", gameId)
                            startActivity(explicitIntent)
                        }
                        main_img_2.setOnClickListener {
                            val explicitIntent = Intent(applicationContext, DetailsActivity::class.java)
                            val gameId = game2.id
                            explicitIntent.putExtra("game_id", gameId)
                            startActivity(explicitIntent)
                        }
                        main_img_3.setOnClickListener {
                            val explicitIntent = Intent(applicationContext, DetailsActivity::class.java)
                            val gameId = game3.id
                            explicitIntent.putExtra("game_id", gameId)
                            startActivity(explicitIntent)
                        }
                        main_img_4.setOnClickListener {
                            val explicitIntent = Intent(applicationContext, DetailsActivity::class.java)
                            val gameId = game4.id
                            explicitIntent.putExtra("game_id", gameId)
                            startActivity(explicitIntent)
                        }
                    }
                }
            }
        }

        service.listGame().enqueue(callback);
    }
}
