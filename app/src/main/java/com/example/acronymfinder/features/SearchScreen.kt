package com.example.acronymfinder.features

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp



// Composable function for the main search screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: AcronymViewModel
) {
    // State variables to track the entered city name
    var sf by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        TextField(
            value = sf,
            onValueChange = { sf = it },
            label = { Text("Enter String") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )

        Button(
            onClick = {
                if (sf.isNotEmpty()) {
                    viewModel.updateString(sf)
                }
                sf = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Find Acronym")
        }

    }
}


