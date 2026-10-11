package au.edu.jcu.assessment.utilityapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import au.edu.jcu.assessment.utilityapp.viewmodel.WeatherViewModel
import au.edu.jcu.assessment.utilityapp.viewmodel.availableCities

@Composable
fun SettingsScreen(viewModel: WeatherViewModel, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))

        // City selection - controls which city the main screen shows
        Text("City", style = MaterialTheme.typography.titleMedium)
        availableCities.forEach { city ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.selectCity(city) }
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = city == viewModel.selectedCity,
                    onClick = { viewModel.selectCity(city) }
                )
                Text(city.name)
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        // Temperature unit - controls how the main screen displays temps
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Use Fahrenheit", style = MaterialTheme.typography.titleMedium)
                Text(
                    "Display temperatures in °F instead of °C",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Switch(
                checked = viewModel.useFahrenheit,
                onCheckedChange = { viewModel.setFahrenheit(it) }
            )
        }
    }
}