package com.willkopec.fetchexercise.repository

import com.willkopec.fetchexercise.api.RetrofitInstance
import com.willkopec.fetchexercise.model.FetchResponse
import com.willkopec.fetchexercise.util.Resource
import javax.inject.Inject

class FetchRepository @Inject constructor(){

    suspend fun getFetchData() : Resource<FetchResponse> {
        val response = try {
            RetrofitInstance.api.getProductData()
        } catch (e: Exception){
            return Resource.Error("An unknown error occured!")
        }
        return Resource.Success(response)
    }

}