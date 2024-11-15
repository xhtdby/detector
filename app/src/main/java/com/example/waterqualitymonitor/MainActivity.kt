package com.example.waterqualitymonitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.waterqualitymonitor.ui.theme.WaterQualityMonitorTheme
import okhttp3.*
import java.io.IOException
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaterQualityMonitorApp()
        }
    }
}
fun sendDataToThingSpeak(url: String) {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url(url)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            // Handle error
        }

        override fun onResponse(call: Call, response: Response) {
            // Handle success
        }
    })
}
@androidx.compose.runtime.Composable
fun WaterQualityMonitorApp() {
    var ph by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("") }
    var temperature by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("") }
    // ... other water quality parameters

    androidx.compose.foundation.layout.Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        androidx.compose.material3.OutlinedTextField(
            value = ph,
            onValueChange = { ph = it },
            label = { androidx.compose.material3.Text("pH (pH*10)") },
            modifier = Modifier.fillMaxWidth()
        )
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(16.dp))
        androidx.compose.material3.OutlinedTextField(
            value = temperature,
            onValueChange = { temperature = it },
            label = { androidx.compose.material3.Text("Temperature (°C)") },
            modifier = Modifier.fillMaxWidth()
        )
        // ... other input fields

        androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(32.dp))

        androidx.compose.material3.Button(onClick = {
            val apiKey = "YOUR_THINGSPEAK_API_KEY"
            val url = "https://api.thingspeak.com/update?api_key=$apiKey" +
                    "&field1=${ph.toFloatOrNull() ?: 0f}" + // pH
                    "&field2=${temperature.toFloatOrNull() ?: 0f}" + // Temperature
                    // ... other parameters
                    sendDataToThingSpeak(url)
        })  {
            androidx.compose.material3.Text("Send Data to ThingSpeak")
        }
    }
}