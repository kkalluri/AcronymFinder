package com.example.acronymfinder.features

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.acronymfinder.data.model.AcronymResponse
import com.example.weatherapp.data.remote.NetworkResult


@Composable
fun AcronymInfo(viewModel: AcronymViewModel, fetchAcronymByString: (String) -> Unit) {
    val enteredSf by viewModel.sf.observeAsState()
    val acronymResponseResult = viewModel.acronymData.observeAsState().value

// Trigger data fetching when the entered city
    LaunchedEffect(enteredSf) {
        println("LaunchedEffect triggered enteredCity with value: ${viewModel.sf.value}")
        viewModel.sf.value?.let { selectedItem ->
            if (!selectedItem.isNullOrBlank()) {
                fetchAcronymByString(selectedItem)
            }
        }
    }


    when (acronymResponseResult) {
        is NetworkResult.Loading -> {
            if ((acronymResponseResult as NetworkResult.Loading).isLoading) {
                LoadingBar()
            }
        }

        is NetworkResult.Success -> {
            var response =
                (acronymResponseResult as NetworkResult.Success<List<AcronymResponse>>).data
            Column(
                Modifier
                    .padding(20.dp)
            ) {
                if(response.size > 0) {
                    AcronymInfo(response[0])
                }else{
                    Text("No Acronyms found")
                }
            }
        }

        is NetworkResult.Failure -> {
            var errorMessage =
                (acronymResponseResult as NetworkResult.Failure<*>).errorMessage
            Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(10.dp))
        }

        else -> {
            Log.d("AcronymInfo", "else block")
        }
    }
}

@Composable
fun AcronymInfo(acronymData: AcronymResponse?) {
    if (acronymData != null) {
        LazyColumn() {
            items(acronymData.lfs) { lf ->
                Text(
                    text = lf.lf,
                    modifier = Modifier
                        .padding(16.dp) // Add padding around the Text
                        .fillMaxWidth(), // Make Text fill the width of the column
                    style = TextStyle(
                        color = Color.Black, // Set text color
                        fontSize = 18.sp, // Set font size
                        fontWeight = FontWeight.Bold // Set font weight
                    )
                )
            }
        }
    }
}


@Composable
fun LoadingBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .width(50.dp)
                .height(4.dp)
                .testTag("LinearProgressIndicator")
        )
    }
}


