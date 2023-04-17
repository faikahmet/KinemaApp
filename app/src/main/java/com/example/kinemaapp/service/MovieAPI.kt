package com.example.kinemaapp.service

import com.example.kinemaapp.data.*
import com.example.kinemaapp.util.Constant
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {


    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = Constant.API_KEY,
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("list/1")
    fun getAllMovies(
        @Query("api_key") apiKey: String = Constant.API_KEY,
        @Query("page") page: Int,
        @Query("sort_by") sort_by: String = "vote_average.desc"
    ): Call<AllMovieModel>


    @GET("movie/top_rated")
    fun getTopRated(
        @Query("api_key") apiKey: String = Constant.API_KEY,
        @Query("language") language: String="en-US",
        @Query("page") page: String="3"
    ): Call<TopRatedMovieModel>



    @GET("movie/upcoming")
    fun getUpcoming(
        @Query("api_key") apiKey: String = Constant.API_KEY,
        @Query("page") page: Int,
        @Query("language") language: String = "en-US"
    ): Call<UpcomingModel>


    @GET("movie/{id}")
    fun getDetail(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = Constant.API_KEY
    ): Call<DetailModel>


}