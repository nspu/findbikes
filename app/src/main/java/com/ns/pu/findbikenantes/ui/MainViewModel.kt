package com.ns.pu.findbikenantes.ui

import androidx.lifecycle.ViewModel
import com.ns.pu.findbikenantes.ui.Result.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UiState {
    data class Success(val markers: List<Marker>) : UiState()
    object Loading : UiState()
    data class Error(val e: Exception) : UiState()
}

data class Marker(
    val title: String,
    val longitude: Double,
    val latitude: Double,
    val places: String,
)

interface StationProvider {
    suspend fun getStation(): Result<List<BikeStationModel>>
}

class MainViewModel(private val stationProvider: StationProvider) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        retrieveStation()
    }

    fun retrieveStation() {
        _uiState.value = UiState.Loading
        CoroutineScope(Dispatchers.IO).launch {
            val result = stationProvider.getStation()
            when (result) {
                is Success -> success(result.data)
                is Error -> failure(result.exception)
            }
        }
    }

    private fun success(stations: List<BikeStationModel>) {
        val markers = stations.map { it.toMarker() }
        _uiState.value = UiState.Success(markers)
    }

    private fun BikeStationModel.toMarker() = Marker(
        title = nameStation,
        latitude = longitude,
        longitude = latitude,
        places = places.toString()
    )

    private fun failure(exception: Exception) {
        //Parse error to have a better display of errors for the user
        _uiState.value = UiState.Error(exception)
    }
}