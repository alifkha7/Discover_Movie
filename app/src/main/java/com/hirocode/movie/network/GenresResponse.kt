package com.hirocode.movie.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GenresResponse(

	@field:SerializedName("genres")
	val genres: List<GenresItem>
)

@Parcelize
data class GenresItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
) : Parcelable
