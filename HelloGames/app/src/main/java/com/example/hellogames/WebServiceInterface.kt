package com.example.hellogames

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServiceInterface {
    @GET("game/list")
    fun listGame(): Call<List<GameList>>

    @GET("game/details")
    fun details(@Query("game_id") game_id: Int): Call<GameDetails>
}