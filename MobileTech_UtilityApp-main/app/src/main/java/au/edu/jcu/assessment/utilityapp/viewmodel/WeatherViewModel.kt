package au.edu.jcu.assessment.utilityapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.edu.jcu.assessment.utilityapp.data.repository.WeatherRepository
import kotlinx.coroutines.launch

// Available cities in the settings screen
data class City(val name: String, val latitude: Double, val longitude: Double)

val availableCities = listOf(
    City("Townsville", -19.2590, 146.8169),
    City("Cairns", -16.9186, 145.7781),
    City("Brisbane", -27.4698, 153.0251),
    City("Sydney", -33.8688, 151.2093),
    City("Melbourne", -37.8136, 144.9631)
)

// Single source of truth for the UI
data class WeatherUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val temperature: Double? = null,
    val feelsLike: Double? = null,
    val humidity: Int? = null,
    val windSpeed: Double? = null,
    val weatherCode: Int? = null
)

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    var uiState by mutableStateOf(WeatherUiState())
        private set

    var selectedCity by mutableStateOf(availableCities.first())
        private set

    var useFahrenheit by mutableStateOf(false)
        private set

    init {
        refreshWeather()
    }

    // Called from SettingsScreen: changing city triggers a new fetch
    fun selectCity(city: City) {
        selectedCity = city
        refreshWeather()
    }

    // Called from SettingsScreen: changes how temperatures are displayed
    fun setFahrenheit(value: Boolean) {
        useFahrenheit = value
    }

    fun refreshWeather() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            try {
                val response = repository.getCurrentWeather(
                    selectedCity.latitude, selectedCity.longitude
                )
                uiState = WeatherUiState(
                    isLoading = false,
                    temperature = response.current.temperature,
                    feelsLike = response.current.feelsLike,
                    humidity = response.current.humidity,
                    windSpeed = response.current.windSpeed,
                    weatherCode = response.current.weatherCode
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = "Failed to load weather. Check your connection."
                )
            }
        }
    }

    // Converts a Celsius value based on the current unit setting
    fun convertTemp(celsius: Double?): Double? =
        celsius?.let { if (useFahrenheit) it * 9 / 5 + 32 else it }
}