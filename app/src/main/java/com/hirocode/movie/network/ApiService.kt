package com.hirocode.movie.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("genre/movie/list")
    suspend fun getGenresMovie(
        @Query("api_key") api_key: String,
    ): GenresResponse

    @GET("discover/movie")
    suspend fun getDiscoverMovie(
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
        @Query("with_genres") with_genres: Int?,
    ): DiscoverResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getReviewsMovie(
        @Path("movie_id") movie_id: Int?,
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): ReviewsResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getVideosMovie(
        @Path("movie_id") movie_id: Int?,
        @Query("api_key") api_key: String,
    ): VideosResponse
}