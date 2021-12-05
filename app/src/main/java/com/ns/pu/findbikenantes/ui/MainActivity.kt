package com.ns.pu.findbikenantes.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.ns.pu.findbikenantes.repository.BikeStationProviderImpl
import com.ns.pu.findbikenantes.ui.theme.FindBikeNantesTheme
import java.lang.Exception

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verifyLocationPermissions()
        setContent {
            FindBikeNantesTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Content(mainViewModel = MainViewModel(BikeStationProviderImpl.make()))
                }
            }
        }
    }

    private fun verifyLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
        }
    }
}


@Composable
fun Content(mainViewModel: MainViewModel) {
    val uiState by mainViewModel.uiState.collectAsState()
    when (uiState) {
        is UiState.Success -> Maps((uiState as UiState.Success).markers)
        UiState.Loading -> Loading()
        is UiState.Error -> Error((uiState as UiState.Error).e, mainViewModel)
    }

}

@Composable
private fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun Error(exception: Exception, mainViewModel: MainViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = exception.localizedMessage)
        Button(content = { Text(text = "Retry") }, onClick = { mainViewModel.retrieveStation() })
    }
}
