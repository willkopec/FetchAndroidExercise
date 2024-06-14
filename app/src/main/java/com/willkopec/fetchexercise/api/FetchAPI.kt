package com.willkopec.fetchexercise.api

import androidx.room.Query
import com.willkopec.fetchexercise.model.FetchResponse
import retrofit2.http.GET

interface FetchAPI {

    @GET("hiring.json")
    suspend fun getProductData(): FetchResponse

}