package au.edu.jcu.assessment.utilityapp.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

// Maps a WMO weather code to an emoji icon + human-readable description
fun weatherInfo(code: Int?): Pair<String, String> = when (code) {
    0 -> "☀️" to "Clear sky"
    1 -> "🌤️" to "Mainly clear"
    2 -> "⛅" to "Partly cloudy"
    3 -> "☁️" to "Overcast"
    45, 48 -> "🌫️" to "Fog"
    in 51..57 -> "🌦️" to "Drizzle"
    in 61..67, in 80..82 -> "🌧️" to "Rain"
    in 71..77 -> "🌨️" to "Snow"
    95, 96, 99 -> "⛈️" to "Thunderstorm"
    else -> "❓" to "Unknown"
}

@Composable
fun WeatherIcon(weatherCode: Int?, modifier: Modifier = Modifier) {
    Text(
        text = weatherInfo(weatherCode).first,
        fontSize = 88.sp,
        modifier = modifier
    )
}

@Composable
fun WeatherDescription(weatherCode: Int?) {
    Text(
        text = weatherInfo(weatherCode).second,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}