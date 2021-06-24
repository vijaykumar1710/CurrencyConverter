package com.learning.vijay.currencyconverter.data

import com.learning.vijay.currencyconverter.data.models.CurrencyResponse
import com.learning.vijay.currencyconverter.util.Constants.ACCESS_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrencyApi {

    @GET("latest?access_key={$ACCESS_KEY}")
    suspend fun getRates(@Query("base") base :String) : Response<CurrencyResponse>

}