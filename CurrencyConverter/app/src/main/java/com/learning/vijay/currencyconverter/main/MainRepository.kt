package com.learning.vijay.currencyconverter.main

import com.learning.vijay.currencyconverter.data.models.CurrencyResponse
import com.learning.vijay.currencyconverter.util.Resources

interface MainRepository {
    suspend fun getRates( base : String) : Resources<CurrencyResponse>
}