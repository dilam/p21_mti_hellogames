package com.example.hellogames

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        var gameId = intent.getIntExtra("game_id", -1)

        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())

        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()

        val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)

        val callback: Callback<GameDetails> = object : Callback<GameDetails> {
            override fun onFailure(call: Call<GameDetails>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed to load data", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<GameDetails>,
                response: Response<GameDetails>
            ) {

                if (response.code() == 200) {

                    val game: GameDetails = response.body()!!;

                    Glide.with(applicationContext)
                        .load(game.picture)
                        .into(details_img_game)

                    details_name.text = game.name
                    details_type.text = game.type
                    details_year.text = game.year.toString()
                    details_nb_players.text = game.players.toString()
                    details_description.text = game.description_en

                    details_more_btn.setOnClickListener {
                        val implicitIntent = Intent(Intent.ACTION_VIEW)
                        implicitIntent.data = Uri.parse(game.url)
                        startActivity(implicitIntent)
                    }
                }
            }
        }

        service.details(gameId).enqueue(callback);
    }
}
