package com.learning.vijay.currencyconverter.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.vijay.currencyconverter.data.models.Rates
import com.learning.vijay.currencyconverter.util.DispatcherProvider
import com.learning.vijay.currencyconverter.util.Resources
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.round

class MainViewModel  @ViewModelInject constructor(
    private val repository: MainRepository,
    private val dispatcher: DispatcherProvider
): ViewModel() {

    sealed class CurrencyEvent {
        class Success( val resultText : String) : CurrencyEvent()
        class Failure( val errorText : String): CurrencyEvent()
        object Loading : CurrencyEvent()
        object Empty : CurrencyEvent()
    }

    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion : StateFlow<CurrencyEvent> = _conversion

    fun convert(
        amountStr : String,
        fromCurrency : String,
        toCurrency : String
    ){
        val fromAmount = amountStr.toFloatOrNull()
        if(fromAmount == null){
            _conversion.value = CurrencyEvent.Failure("not valid amount")
            return
        }

        viewModelScope.launch (dispatcher.io){
            _conversion.value = CurrencyEvent.Loading
            when( val ratesResponse = repository.getRates(  fromCurrency) ){
                is Resources.Error -> _conversion.value = CurrencyEvent.Failure( ratesResponse.message!!)
                is Resources.Success -> {
                    val rates = ratesResponse.data!!.rates
                    val rate = getRateForCurrency(toCurrency , rates)
                    if(rate == null ){
                        _conversion.value = CurrencyEvent.Failure( "UnExpected Error")
                    }else{
                        val convertedValue = getResult(fromAmount as Double , rate as Double)
                        _conversion.value = CurrencyEvent.Success(" $fromAmount $fromCurrency = $convertedValue $toCurrency ")
                    }
                }
            }
        }

    }

    private fun getResult(fromAmount: Double, rate: Double ): Double {
        return round(fromAmount * rate * 100)/100
    }


    private fun getRateForCurrency(currency: String, rates: Rates) = when (currency) {
        "CAD" -> rates.CAD
        "HKD" -> rates.HKD
        "ISK" -> rates.ISK
        "EUR" -> rates.EUR
        "PHP" -> rates.PHP
        "DKK" -> rates.DKK
        "HUF" -> rates.HUF
        "CZK" -> rates.CZK
        "AUD" -> rates.AUD
        "RON" -> rates.RON
        "SEK" -> rates.SEK
        "IDR" -> rates.IDR
        "INR" -> rates.INR
        "BRL" -> rates.BRL
        "RUB" -> rates.RUB
        "HRK" -> rates.HRK
        "JPY" -> rates.JPY
        "THB" -> rates.THB
        "CHF" -> rates.CHF
        "SGD" -> rates.SGD
        "PLN" -> rates.PLN
        "BGN" -> rates.BGN
        "CNY" -> rates.CNY
        "NOK" -> rates.NOK
        "NZD" -> rates.NZD
        "ZAR" -> rates.ZAR
        "USD" -> rates.USD
        "MXN" -> rates.MXN
        "ILS" -> rates.ILS
        "GBP" -> rates.GBP
        "KRW" -> rates.KRW
        "MYR" -> rates.MYR
        else -> null
    }

}

