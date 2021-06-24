package com.learning.vijay.currencyconverter.di

import com.learning.vijay.currencyconverter.data.CurrencyApi
import com.learning.vijay.currencyconverter.main.DefaultMainRepository
import com.learning.vijay.currencyconverter.main.MainRepository
import com.learning.vijay.currencyconverter.util.Constants.BASE_URL
import com.learning.vijay.currencyconverter.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton



@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

        @Singleton
        @Provides
        fun providesCurrencyApi() : CurrencyApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyApi::class.java)

        @Singleton
        @Provides
        fun provideMainRepository( currencyApi: CurrencyApi) : MainRepository = DefaultMainRepository(currencyApi)


        @Singleton
        @Provides
        fun provideDispatchers() : DispatcherProvider = object : DispatcherProvider{
            override val main: CoroutineDispatcher
                get() = Dispatchers.Main
            override val io: CoroutineDispatcher
                get() = Dispatchers.IO
            override val default: CoroutineDispatcher
                get() = Dispatchers.Default
            override val unconfined: CoroutineDispatcher
                get() = Dispatchers.Unconfined
        }
}