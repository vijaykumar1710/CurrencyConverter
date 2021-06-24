package com.learning.vijay.currencyconverter.main

import com.learning.vijay.currencyconverter.data.CurrencyApi
import com.learning.vijay.currencyconverter.data.models.CurrencyResponse
import com.learning.vijay.currencyconverter.util.Resources
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val currencyApi: CurrencyApi
) : MainRepository{

    override suspend fun getRates( base: String): Resources<CurrencyResponse> {
        return try{
            val response = currencyApi.getRates(base)
            val result = response.body()
            if(response.isSuccessful && result != null){
                return Resources.Success(result)
            }else{
                return Resources.Error(response.message())
            }
        }catch (exception : Exception){
            Resources.Error(exception.message ?: "An Error occurred")
        }
    }

}