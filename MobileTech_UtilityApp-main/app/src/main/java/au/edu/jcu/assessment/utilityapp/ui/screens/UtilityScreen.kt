package au.edu.jcu.assessment.utilityapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import au.edu.jcu.assessment.utilityapp.ui.components.WeatherDescription
import au.edu.jcu.assessment.utilityapp.ui.components.WeatherIcon
import au.edu.jcu.assessment.utilityapp.viewmodel.WeatherViewModel
import java.util.Locale

@Composable
fun UtilityScreen(viewModel: WeatherViewModel, modifier: Modifier = Modifier) {
    val state = viewModel.uiState

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when {
            state.isLoading -> CircularProgressIndicator()

            state.error != null -> {
                Text(state.error, color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(16.dp))
                Button(onClick = { viewModel.refreshWeather() }) {
                    Text("Retry")
                }
            }

            else -> {
                Text(
                    text = viewModel.selectedCity.name,
                    style = MaterialTheme.typography.headlineSmall
                )
                WeatherIcon(state.weatherCode)
                Text(
                    // "25.3°C" or "77.5°F" depending on the setting
                    text = String.format(
                        Locale.getDefault(), "%.1f%s",
                        viewModel.convertTemp(state.temperature),
                        if (viewModel.useFahrenheit) "°F" else "°C"
                    ),
                    style = MaterialTheme.typography.displayLarge
                )
                WeatherDescription(state.weatherCode)

                Spacer(Modifier.height(32.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    InfoCard(
                        label = "Feels like",
                        value = String.format(
                            Locale.getDefault(), "%.1f°",
                            viewModel.convertTemp(state.feelsLike) ?: 0.0
                        )
                    )
                    InfoCard(label = "Humidity", value = "${state.humidity ?: 0}%")
                    InfoCard(label = "Wind", value = "${state.windSpeed ?: 0.0} km/h")
                }
            }
        }
    }
}

@Composable
private fun InfoCard(label: String, value: String) {
    Card(modifier = Modifier.width(110.dp)) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, style = MaterialTheme.typography.titleLarge)
            Text(
                label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}