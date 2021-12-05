package com.ns.pu.findbikenantes.repository

import com.ns.pu.findbikenantes.repository.dto.Record
import com.ns.pu.findbikenantes.ui.BikeStationModel
import com.ns.pu.findbikenantes.ui.Result
import com.ns.pu.findbikenantes.ui.StationProvider

class BikeStationProviderImpl(private val bikeStationService: BikeStationService) : StationProvider {

    companion object {
        fun make() = BikeStationProviderImpl(bikeStationService = bikeStationServiceRetrofit)
    }

    override suspend fun getStation(): Result<List<BikeStationModel>> {
        return try {
            val response = bikeStationService.stations()
            val bikeStationDto = response.body()
            val bikeStationModel = bikeStationDto?.records?.map { it.toStation() }
            Result.Success(bikeStationModel ?: listOf())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private fun Record.toStation() = BikeStationModel(
        nameStation = fields.nom,
        places = fields.capacite_num,
        latitude = fields.geo_shape.coordinates[0],
        longitude = fields.geo_shape.coordinates[1]
    )
}