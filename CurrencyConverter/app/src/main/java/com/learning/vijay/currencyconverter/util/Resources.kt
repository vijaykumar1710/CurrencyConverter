package com.learning.vijay.currencyconverter.util

sealed class Resources<T>( val data : T? , val message: String?) {

    class Success<T>(data: T) : Resources<T>( data , null)
    class Error<T>(message: String) : Resources<T>( null , message)

}