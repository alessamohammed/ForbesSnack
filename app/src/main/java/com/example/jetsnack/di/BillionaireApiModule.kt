package com.example.jetsnack.di

import com.example.jetsnack.data.Api.ApiConstants
import com.example.jetsnack.data.Api.BillionaireApi
import com.example.jetsnack.data.repository.BillionaireRepository
import com.example.jetsnack.data.repository.BillionaireRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BillionaireApiModule {

    @Provides
    @Singleton
    fun provideApi(builder: Retrofit.Builder): BillionaireApi {
        return builder
            .build()
            .create(BillionaireApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

    }

    @Provides
    @Singleton
    fun provideBillionaireRepository(billionaireApi: BillionaireApi): BillionaireRepository {
        return BillionaireRepositoryImpl(billionaireApi)
    }
}