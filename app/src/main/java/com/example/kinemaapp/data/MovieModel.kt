package com.example.kinemaapp.data


import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("release_date") val releaseDate: String
)
data class GetMoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<MovieModel>,
    @SerializedName("total_pages") val pages: Int
)