package com.ns.pu.findbikenantes.repository

import com.ns.pu.findbikenantes.repository.dto.BikeStation
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://data.nantesmetropole.fr")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
val bikeStationServiceRetrofit: BikeStationService = retrofit.create(BikeStationService::class.java)


interface BikeStationService {

    @GET("api/records/1.0/search/?dataset=244400404_stations-velos-libre-service-nantes-metropole")
    suspend fun stations(): Response<BikeStation>
}